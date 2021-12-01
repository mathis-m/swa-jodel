import axios from "axios";
import {storeAccessor} from "../redux/store-accessor";

export class BaseClient {
    instance;
    constructor(baseURL) {
        this.instance = axios.create({
            baseURL: "http://localhost:8080" + baseURL,
        });

        this.initializeResponseInterceptor();
    }

    getCurrentAuthHeader = () => {
        const authorization = storeAccessor.store.getState().user.header;
        if(authorization == null)
            return {};
        return {Authorization: authorization};
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