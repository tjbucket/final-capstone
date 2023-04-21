package com.techelevator.dao;

import com.techelevator.model.Unit;
import com.techelevator.model.WorkOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcWorkOrderDaoTests extends BaseDaoTests {

    private static final WorkOrder WORK_ORDER_1 = new WorkOrder(1, 1001, "Plumbing", "Leaky faucet", false);
    private static final WorkOrder WORK_ORDER_2 = new WorkOrder(2, 1001, "Electrical", "Porch light out", false);
    private static final WorkOrder WORK_ORDER_3 = new WorkOrder(3, 2001, "HVAC", "Heating broken", false);
    private static final WorkOrder WORK_ORDER_4 = new WorkOrder(4, 3001, "HVAC", "AC broken", false);
    private static final WorkOrder WORK_ORDER_5 = new WorkOrder(4, 2001, "Electrical", "Outlet unpowered", false);

    private JdbcWorkOrderDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcWorkOrderDao(jdbcTemplate);
    }

    @Test
    public void listAllWorkOrders_returns_all_workOrders() {
        List<WorkOrder> result = sut.listAllWorkOrders();

        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void listAllUnits_returns_all_units() {
        List<Unit> result = sut.listAllUnits();

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(1001, result.get(0).getUnitNumber());
        Assert.assertEquals(2001, result.get(1).getUnitNumber());
        Assert.assertEquals(3001, result.get(2).getUnitNumber());
    }

    @Test
    public void listAllStatuses_returns_all_statuses() {
        List<String> result = sut.listAllStatuses();

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("Open", result.get(0));
        Assert.assertEquals("Pending", result.get(1));
        Assert.assertEquals("Closed", result.get(2));
    }

    @Test
    public void listAllAreas_returns_all_areas() {
        List<String> result = sut.listAllAreas();

        Assert.assertNotNull(result);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals("Plumbing", result.get(0));
        Assert.assertEquals("Electrical", result.get(1));
        Assert.assertEquals("HVAC", result.get(2));
    }

    @Test
    public void createWorkOrder_creates_valid_workOrder() {
        WorkOrder workOrder = new WorkOrder();


    }

    @Test
    public void viewWorkOrder_given_valid_workOrderId_returns_workOrder() {

    }

//    implement this?
//    @Test
//    public void viewWorkOrder_given_invalid_workOrderId_returns_null(){
//
//    }

    @Test
    public void updateWorkOrder_updates_existing_workOrder() {

    }

}
