import axios from 'axios';

export default{
    getSixMonths(){
        return axios.get('/metric/6m')
    },
    getThisMonth(){
        return axios.get('metric')
    },
    getSixMonthsRatings(){
        return axios.get('ratings')
    }
}