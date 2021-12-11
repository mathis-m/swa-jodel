import {BaseClient} from "../base-client";

class LoginApi extends BaseClient {
    constructor() {
        super("/login");
    }

    loginLocal = async (basicHeader) =>  await this.instance
        .post('/local', undefined, {
            headers: {
                'Authorization': basicHeader
            },
            withCredentials: true
        })

    loginGoogle = async (bearerHeader) =>  await this.instance
        .post('/google', undefined, {
            headers: {
                'Authorization': bearerHeader
            },
            withCredentials: true
        })
}

export const loginApi = new LoginApi();