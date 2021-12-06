import axios from 'axios';

const CATERER_API_BASE_URL = "http://localhost:8080/caterer";

class CatererService {

    getCaterers(){
        return axios.get(CATERER_API_BASE_URL+'/all');
    }

    createCaterer(caterer){
        return axios.post(CATERER_API_BASE_URL+'/save', caterer);
    }

    getCatererById(id){
        return axios.get(CATERER_API_BASE_URL + '/id/' + id);
    }

    updateCaterer(caterer, id){
        return axios.put(CATERER_API_BASE_URL + '/update/' + id, caterer);
    }

    deleteCaterer(id){
        return axios.delete(CATERER_API_BASE_URL + '/delete/' + id);
    }
}

export default new CatererService()