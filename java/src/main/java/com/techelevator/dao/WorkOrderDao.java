package com.techelevator.dao;

import com.techelevator.model.Comment;
import com.techelevator.model.Photo;
import com.techelevator.model.Unit;
import com.techelevator.model.WorkOrder;

import java.util.List;

public interface WorkOrderDao {
    List<WorkOrder> filterBySubmitterId(int submitterId);
    List<WorkOrder> listAllWorkOrders();
    List<Unit> listAllUnits();
    List<String> listAllStatuses();
    List<String> listAllAreas();
    boolean createWorkOrder(WorkOrder workOrder);
    WorkOrder viewWorkOrder(int workOrderId);
    void updateWorkOrder(WorkOrder workOrder);
    boolean createComment(Comment comment);
    boolean createPhoto(Photo photo);
}
