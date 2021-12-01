import {BaseClient} from "../base-client";

class UserApi extends BaseClient {
    constructor() {
        super("/users");
    }

    getCurrentUser = async (header) => await this.instance
        .get('/me', {
            headers: {
                'Authorization': header
            }
        });

    createGoogleUser = async (idToken, userName) =>  await this.instance
        .post('/register/google', {userName}, {
            headers: {
                'Authorization': `Bearer ${idToken}`
            }
        })

    createLocalUser = async (userName, password) =>  await this.instance
        .post('/register/local', {userName, password})
}

export const userApi = new UserApi();