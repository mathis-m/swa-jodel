import axios from "axios";

export class BaseClient {
    instance;
    constructor(baseURL) {
        this.instance = axios.create({
            baseURL: "http://localhost:8080" + baseURL,
        });

        this.initializeResponseInterceptor();
    }

    initializeResponseInterceptor = () => {
        this.instance.interceptors.response.use(
            this.handleResponse,
            this.handleError,
        );
    };

    handleResponse = ({ data }) => {
        return data;
    };

    handleError = (error) => Promise.reject(error);
}