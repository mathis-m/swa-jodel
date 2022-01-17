import {BaseClient} from "../base-client";

class LoginApi extends BaseClient {
    constructor() {
        super("");
    }

    loginLocal = async (basicHeader) =>  await this.instance
        .post('/login/local', undefined, {
            headers: {
                'Authorization': basicHeader
            },
            withCredentials: true
        })

    loginGoogle = async (bearerHeader) =>  await this.instance
        .post('/login/google', undefined, {
            headers: {
                'Authorization': bearerHeader
            },
            withCredentials: true
        })

    loginFacebook = async (bearerHeader) =>  await this.instance
        .post('/login/facebook', undefined, {
            headers: {
                'Authorization': bearerHeader
            },
            withCredentials: true
        })

    logout = async () => await this.instance
        .post('/logout', undefined, {
            withCredentials: true
        })
}

export const loginApi = new LoginApi();