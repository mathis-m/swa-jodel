import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import moment from "moment";

const initialState = {
    posts: [
        {
            id: 0,
            text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam volutpat arcu ex, at interdum nibh ullamcorper in. Cras euismod arcu vel iaculis vehicula. Nam id nulla velit. Morbi tempus neque eget porttitor pellentesque. Vestibulum ullamcorper neque ante, at euismod lectus mollis quis. Etiam placerat eu sapien eget rhoncus. Quisque risus nibh,",
            user: "test",
            voteCount: 65,
            date: moment().add(-30, "seconds").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}, {userId: 2, text: "So toll"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 1,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 65,
            date: moment().add(-15, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 2,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-20, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}, {userId: 2, text: "So toll"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 3,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-50, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 4,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-90, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 5,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-120, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 6,
            text: " a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-130, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 7,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-2000, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 8,
            text: "test",
            user: "test",
            voteCount: 65,
            date: moment().add(-20000, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
        {
            id: 9,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            voteCount: 33,
            date: moment().add(-200000, "minutes").format(),
            locationText: "Berlin",
            comments: [{userId: 1, text: "Pimmel"}],
            hasUpVoted: false,
            hasDownVoted: false
        },
    ],
    status: "idle",
    error: null,
    lastDate: moment().format()
}

const addPost =
    (state, post) => {
        state.posts.push(post)
    }
export const fetchNextLatestPosts = createAsyncThunk('user/fetchNextLatestPosts',
    async () => {
        return [];
    });

export const latestPostSlice = createSlice({
    name: 'latest-posts',
    initialState,
    reducers: {
        postAdded: addPost,
    },
    extraReducers(builder) {
        builder
            .addCase(fetchNextLatestPosts.pending, (state, action) => {
                state.status = 'loading';
            })
            .addCase(fetchNextLatestPosts.fulfilled, (state, action) => {
                const posts = action.payload;
                state.status = 'succeeded';

                if (posts.length === 0) {
                    state.hasMore = false;
                    return;
                }

                const insertIndex = state.breaks.findIndex(b => b.date > posts[0].date);
                if (insertIndex === -1) {
                    state.posts.push(...posts);
                } else {
                    state.posts.splice(insertIndex, 0, ...posts);
                }
            })
            .addCase(fetchNextLatestPosts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            });
    }
})

export const {
    postAdded,
} = latestPostSlice.actions

const state = (state) => state.newestPosts;

export const selectPosts = createSelector(
    state,
    state => state.posts
);
export const usePosts = () => useSelector(selectPosts);

export default latestPostSlice.reducer;