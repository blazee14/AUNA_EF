import axios from "axios";

const LOGUEO_BASE_REST_API_URL = `${import.meta.env.VITE_API_URL}/api/v1/auth/login`;

class LogueoService {
    comprobarLogueo(loginData) {
        return axios.post(LOGUEO_BASE_REST_API_URL, loginData);
    }
}

const logueoService = new LogueoService();
export default logueoService;
