import {BaseClient} from "../base-client";

class UserApi extends BaseClient {
    constructor() {
        super("/users");
    }

    getCurrentUser = async () => await this.instance
        .get('/me', {withCredentials: true});

    createGoogleUser = async (idToken, userName) =>  await this.instance
        .post('/register/google', {userName}, {
            headers: {
                'Authorization': `Bearer ${idToken}`
            }
        })

    createLocalUser = async (userName, password) =>  await this.instance
        .post('/register/local', {userName, password})

    updateLocation = async (lat, lon) =>  await this.instance
        .post('/my/location', {lon, lat}, {withCredentials: true})
}

export const userApi = new UserApi();