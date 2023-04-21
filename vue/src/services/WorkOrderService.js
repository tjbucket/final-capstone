import axios from "axios";

// const http = axios.create({
//     baseURL: "http://localhost:9000"
// });

export default {
    create(workOrder){
        return axios.post(`/workOrder`, workOrder);
    },
    getWorkOrders() {
        return axios.get('/workOrder');
    },
    get(id) {
        return axios.get(`/workOrder/${id}`);
    },
    getUnits(){
        return axios.get('/workOrder/units');
    },
    updateWorkOrder(workOrder){
        return axios.put('/workOrder/update', workOrder);
    },
    getStatuses(){
        return axios.get('/workOrder/statuses');
    },
    getAreas(){
        return axios.get('/workOrder/areas');
    },
    createComment(comment){
        return axios.post('/workOrder/comment', comment);

    }
}