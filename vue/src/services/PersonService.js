import axios from "axios";

export default {
    getPersonalDetails() {
        return axios.get('/person');
    },
    setPersonalInfo(data) {
        return axios.post('/person', data);
    },
    getTenants() {
        return axios.get('/tenants');
    },
    getTenant(id) {
        return axios.get(`/tenants/${id}`);
    },
    getPendingTenants() {
        return axios.get('/pending');
    },
    updatePendingUser(data){
        return axios.put('/pending', data)
    },
    deleteUser(id) {
        return axios.delete(`/tenants/${id}`)
    },
    setTenantPropComment(data, id) {
        const config = { headers: { 'Content-Type': 'application/json' } };
        return axios.put(`/tenants/${id}/comment/`, data, config);
    },
    getAllPersonnel(){
        return axios.get('/personnel');
    }
}