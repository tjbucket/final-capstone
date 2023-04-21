package com.techelevator.dao;

import com.techelevator.model.Comment;
import com.techelevator.model.Photo;
import com.techelevator.model.Unit;
import com.techelevator.model.WorkOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcWorkOrderDao implements WorkOrderDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkOrderDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<WorkOrder> filterBySubmitterId(int submitterId) {
        return null;
    }

    @Override
    public List<WorkOrder> listAllWorkOrders() {
        List<WorkOrder> workOrders = new ArrayList<>();
        String sql = "SELECT order_id, submitter_id, unit_number, area_id, status_id, order_description, when_posted, is_emergency, rating " +
                "FROM work_orders WHERE status_id != 4 ORDER BY is_emergency DESC, status_id ASC, order_id;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            workOrders.add(mapRowToWorkOrder(results));
        }
        return workOrders;
    }

    @Override
    public List<Unit> listAllUnits() {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT unit_number, unit_description FROM units";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Unit unit = new Unit();
            unit.setUnitNumber(results.getInt("unit_number"));
            unit.setUnitDescription(results.getString("unit_description"));
            units.add(unit);
        }
        return units;
    }

    @Override
    public List<String> listAllStatuses() {
        List<String> descriptions = new ArrayList<>();
        String sql = "SELECT status_description FROM work_statuses";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            descriptions.add(results.getString("status_description"));
        }
        return descriptions;
    }

    @Override
    public List<String> listAllAreas() {
        List<String> descriptions = new ArrayList<>();
        String sql = "SELECT area_description FROM work_areas";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            descriptions.add(results.getString("area_description"));
        }
        return descriptions;
    }


    @Override
    public boolean createWorkOrder(WorkOrder workOrder) {
        String sql = "INSERT INTO work_orders(submitter_id,unit_number,area_id,status_id,order_description,is_emergency)\n" +
                "VALUES (?,?,?,1,?,?) RETURNING order_id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class,
                workOrder.getSubmitterId(),
                workOrder.getUnitNumber(),
                getAreaIdFromName(workOrder.getAreaName()),
                workOrder.getOrderDescription(),
                workOrder.isEmergency());
        workOrder.setOrderId(newId);
        return createWorkOrderPhotos(workOrder) && newId != null;
    }

    public boolean createWorkOrderPhotos(WorkOrder workOrder){
        String sql = "INSERT INTO work_order_images(order_id, img_secure_url) VALUES (?, ?) RETURNING image_id";
        if(workOrder.getImages() != null) {
            for (String image : workOrder.getImages()) {
                Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, workOrder.getOrderId(), image);
                if (newId == null) return false;
            }
            return true;
        }
        return false;

    }

    @Override
    public WorkOrder viewWorkOrder(int workOrderId) {
        WorkOrder workOrder = new WorkOrder();
        String sql = "SELECT order_id, submitter_id, unit_number, area_id, status_id, order_description, when_posted, is_emergency, rating\n" +
                "FROM work_orders WHERE order_id = ?";

        // Expected to return only one WorkOrder
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, workOrderId);

        // TODO refactor to if-else to check if workOrderId not found?
        while (results.next()) {
            workOrder = mapRowToWorkOrder(results);
        }

        List<String> images = new ArrayList<>();
        String sqlForImages = "SELECT img_secure_url FROM work_order_images WHERE order_id = ?";
        SqlRowSet resultsForImages = jdbcTemplate.queryForRowSet(sqlForImages, workOrderId);

        while(resultsForImages.next()){
            images.add(resultsForImages.getString("img_secure_url"));
        }
        workOrder.setImages(images);

        List<Comment> commentList = new ArrayList<>();
        String sqlForComments = "SELECT comment_id, order_id, poster_id, comment_description, when_posted\n" +
                "FROM comments WHERE order_id = ?;";
        SqlRowSet resultsForComments = jdbcTemplate.queryForRowSet(sqlForComments, workOrderId);

        while (resultsForComments.next()) {
            Comment comment = mapRowToComment(resultsForComments);

            List<Photo> photoList = new ArrayList<>();
            String sqlForPhotos = "SELECT photo_id, comment_id, location\n" +
                    "FROM photos WHERE comment_id = ?;";
            SqlRowSet resultsForPhotos = jdbcTemplate.queryForRowSet(sqlForPhotos, comment.getCommentId());

            while (resultsForPhotos.next()) {
                photoList.add(mapRowToPhoto(resultsForPhotos));
            }

            comment.setPhotos(photoList);
            commentList.add(comment);
        }

        workOrder.setOrderComments(commentList);
        return workOrder;
    }

    @Override
    public void updateWorkOrder(WorkOrder workOrder) {
        String sql = "UPDATE work_orders SET status_id = ?, rating = ?, is_emergency = ?" +
                     (workOrder.getStatusName().equals("Accepted")?", when_accepted = LOCALTIMESTAMP ":" ") +
                     "WHERE order_id = ?;";

        jdbcTemplate.update(sql, getStatusIdFromName(workOrder.getStatusName()), workOrder.getRating(), workOrder.isEmergency(), workOrder.getOrderId());
    }

    @Override
    public boolean createComment(Comment comment) {
        String sql = "INSERT INTO comments (order_id, poster_id, comment_description)\n" +
                "VALUES (?,?,?) RETURNING comment_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, comment.getOrderId(), comment.getPosterId(),
                comment.getCommentDescription());
        return newId != null;
    }

    @Override
    public boolean createPhoto(Photo photo) {
        String sql = "INSERT INTO photos (comment_id, location)\n" +
                "VALUES (?,?) RETURNING photo_id;";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, photo.getCommentId(), photo.getImage());
        return newId != null;
    }

    // HELPER FUNCTIONS

    private WorkOrder mapRowToWorkOrder(SqlRowSet results) {
        WorkOrder workOrder = new WorkOrder();
            workOrder.setOrderId(results.getInt("order_id"));
            workOrder.setSubmitterId(results.getInt("submitter_id"));
            workOrder.setUnitNumber(results.getInt("unit_number"));
            workOrder.setAreaName(getAreaNameFromId(results.getInt("area_id")));
            workOrder.setStatusName(getStatusNameFromId(results.getInt("status_id")));
            workOrder.setOrderDescription(results.getString("order_description"));
            workOrder.setWhenPosted(results.getTimestamp("when_posted"));
            workOrder.setEmergency(results.getBoolean("is_emergency"));
            workOrder.setRating(results.getInt("rating"));
        return workOrder;
    }

    private Comment mapRowToComment(SqlRowSet results) {
        Comment comment = new Comment();
            comment.setCommentId(results.getInt("comment_id"));
            comment.setOrderId(results.getInt("order_id"));
            comment.setPosterId(results.getInt("poster_id"));
            comment.setCommentDescription(results.getString("comment_description"));
            comment.setWhenPosted(results.getTimestamp("when_posted"));
        return comment;
    }

    private Photo mapRowToPhoto(SqlRowSet results) {
        Photo photo = new Photo();
            photo.setPhotoId(results.getInt("photo_id"));
            photo.setCommentId(results.getInt("comment_id"));
            photo.setImage(results.getString("location"));
        return photo;
    }

    private int getAreaIdFromName(String areaName) {
        String sql = "SELECT area_id FROM work_areas WHERE area_description ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, areaName);
    }

    private String getAreaNameFromId(int areaId) {
        String sql = "SELECT area_description FROM work_areas WHERE area_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, areaId);
    }

    private int getStatusIdFromName(String statusName) {
        String sql = "SELECT status_id FROM work_statuses WHERE status_description ILIKE ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, statusName);
    }

    private String getStatusNameFromId(int statusId) {
        String sql = "SELECT status_description FROM work_statuses WHERE status_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, statusId);
    }
}