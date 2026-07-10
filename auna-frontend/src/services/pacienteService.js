import axios from "axios";

const PACIENTE_BASE_REST_API_URL = `${import.meta.env.VITE_API_URL}/api/v1/pacientes`;

class PacienteService {
    getAllPacientes() {
        return axios.get(PACIENTE_BASE_REST_API_URL);
    }
}

const pacienteService = new PacienteService();
export default pacienteService;