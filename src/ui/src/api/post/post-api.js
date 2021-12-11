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
        .get(`/${postId}/vote`);
    votePostUp = async (postId) => await this.instance
        .post(`/${postId}/vote/up`, undefined, {withCredentials: true});
    votePostDown = async (postId) => await this.instance
        .post(`/${postId}/vote/down`, undefined, {withCredentials: true});

    createPost = async (post) => await this.instance
        .post('', post, {withCredentials: true});
}

export const postApi = new PostApi();