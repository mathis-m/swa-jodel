import {createAsyncThunk, createSelector, createSlice} from "@reduxjs/toolkit";
import {useSelector} from "react-redux";
import moment from "moment";
import produce from "immer";

const initialState = {
    posts: [
        {
            id: 0,
            text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam volutpat arcu ex, at interdum nibh ullamcorper in. Cras euismod arcu vel iaculis vehicula. Nam id nulla velit. Morbi tempus neque eget porttitor pellentesque. Vestibulum ullamcorper neque ante, at euismod lectus mollis quis. Etiam placerat eu sapien eget rhoncus. Quisque risus nibh,",
            user: "test",
            date: moment().add(-30, "seconds").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}, {userId: 2, text: "So toll"}],
        },
        {
            id: 1,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-15, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 2,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-20, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}, {userId: 2, text: "So toll"}]
        },
        {
            id: 3,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-50, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 4,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-90, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 5,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-120, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 6,
            text: " a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-130, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 7,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-2000, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 8,
            text: "test",
            user: "test",
            date: moment().add(-20000, "minutes").format(),
            locationText: "Stuttgart",
            comments: [{userId: 1, text: "Hihihi wow"}]
        },
        {
            id: 9,
            text: "a finibus consequat quis in urna. Nullam magna magna, blandit eget viverra id, ornare eget metus. Nunc placerat posuere malesuada. Aenean fermentum ac est quis ornare. Quisque vel commodo mi. Sed tellus mi, tincidunt id nibh a, iaculis auctor risus. Donec euismod tortor id fermentum egestas. Duis sagittis vulputate lacus at lacinia. Aliquam consequat porttitor porttitor. Nam feugiat viverra odio non euismod. Etiam cursus venenatis tortor, q",
            user: "other user",
            date: moment().add(-200000, "minutes").format(),
            locationText: "Berlin",
            comments: [{userId: 1, text: "Pimmel"}]
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
export const fetchNextHighestVotedPosts = createAsyncThunk('user/fetchNextHighestVotedPosts',
    async () => {
        return [];
    });

export const highestVotingSlice = createSlice({
    name: 'highest-voting',
    initialState,
    reducers: {
        postAdded: addPost,
    },
})

export const {
    postAdded,
} = highestVotingSlice.actions

const state = (state) => state.highestVotesPosts;
const votingState = (state) => state.votes.votes;

export const selectPosts = createSelector(
    [
        state,
        votingState
    ],
    (state, votingState) => {
        const findVote = (id) => votingState.find(v => v.postId === id);
        return produce(state, draft => {
            draft.posts.sort((a, b) => (findVote(a.id).voteCount < findVote(b.id).voteCount) ? 1 : -1)
        }).posts;
    }
);

export const usePosts = () => useSelector(selectPosts);

export default highestVotingSlice.reducer;