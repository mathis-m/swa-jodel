import {BaseClient} from "../base-client";

class PostApi extends BaseClient {
    constructor() {
        super("/posts");
    }

    getNewestPosts = async (page) => await this.instance
        .get('/newest', {
            params: {page, limit: 15}
        });
    getHighestVotedPosts = async (page) => await this.instance
        .get('/most-votes', {
            params: {page, limit: 15}
        });
    getMostCommentsPosts = async (page) => await this.instance
        .get('/most-comments', {
            params: {page, limit: 15}
        });
    getVotingForPost = async (postId) => await this.instance
        .get(`/${postId}/vote`, {
            headers: this.getCurrentAuthHeader()
        });
    votePostUp = async (postId) => await this.instance
        .post(`/${postId}/vote/up`, undefined, {
            headers: this.getCurrentAuthHeader()
        });
    votePostDown = async (postId) => await this.instance
        .post(`/${postId}/vote/down`, undefined,{
            headers: this.getCurrentAuthHeader()
        });
}

export const postApi = new PostApi();