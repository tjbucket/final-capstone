import axios from "axios";

export default {
    getUnits(){
        return axios.get('/units');
    },
    getUnitById(id){
        return axios.get(`/units/${id}`);
    },
    setUnitPropComment(data, id){
        const config = { headers: {'Content-Type': 'application/json'} };
        return axios.put(`/units/${id}/comment/`, data, config);
    }
}