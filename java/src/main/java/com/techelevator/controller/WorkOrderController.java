package com.techelevator.controller;

import com.techelevator.dao.JdbcPersonDao;
import com.techelevator.dao.JdbcWorkOrderDao;
import com.techelevator.model.Comment;
import com.techelevator.model.Unit;
import com.techelevator.model.WorkOrder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
@RequestMapping("/workOrder")
public class WorkOrderController {
    JdbcWorkOrderDao workOrderDao;
    JdbcPersonDao personDao;

    public WorkOrderController(JdbcWorkOrderDao workOrderDao, JdbcPersonDao personDao) {
        this.workOrderDao = workOrderDao;
        this.personDao = personDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createWorkOrder(@RequestBody WorkOrder workOrder, Principal principal){
        workOrder.setSubmitterId(personDao.getPersonIdFromUserName(principal.getName()));
        boolean success = workOrderDao.createWorkOrder(workOrder);
        if (!success){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Work order not created");
        }
    }

    @GetMapping("")
    public List<WorkOrder> viewWorkOrders(){
        return workOrderDao.listAllWorkOrders();
    }

    @GetMapping("/units")
    public List<Unit> getAllUnitNumbers(){
        return workOrderDao.listAllUnits();
    }

    @GetMapping("/statuses")
    public List<String> getAllStatuses(){
        return workOrderDao.listAllStatuses();
    }

    @GetMapping("/areas")
    public List<String> getAllAreas(){
        return workOrderDao.listAllAreas();
    }

    @GetMapping("/{id}")
    public WorkOrder viewWorkOrderById(@PathVariable int id){
        return workOrderDao.viewWorkOrder(id);
    }

    @PutMapping("/update")
    public void updateWorkOrder(@RequestBody WorkOrder workOrder){
       workOrderDao.updateWorkOrder(workOrder);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comment")
    public void createComment(@RequestBody Comment comment, Principal principal){
        comment.setPosterId(personDao.getPersonIdFromUserName(principal.getName()));
        workOrderDao.createComment(comment);
    }
}
