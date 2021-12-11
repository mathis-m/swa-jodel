package de.swa.rest.posts;

import de.swa.infrastructure.repositories.CommentRepository;
import de.swa.infrastructure.repositories.PostRepository;
import de.swa.infrastructure.repositories.VotingRepository;
import de.swa.rest.posts.dto.*;
import de.swa.rest.posts.dto.factories.CommentEntityToResponseDtoFactory;
import de.swa.rest.posts.dto.factories.PostEntityToResponseDtoFactory;
import de.swa.rest.posts.dto.factories.VotingEntityToResponseDtoFactory;
import de.swa.services.UserContextService;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Path("/posts")
public class PostResource {
    @Inject
    PostRepository postRepository;
    @Inject
    VotingRepository votingRepository;
    @Inject
    CommentRepository commentRepository;
    @Inject
    UserContextService userContextService;

    @GET()
    @Path("/newest")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<PostResponseDto>> getNewestPosts(@RestQuery("page") Integer page, @RestQuery("limit") Integer limit) {
        if (page == null || limit == null)
            throw new BadRequestException("page and limit must be set");
        var entities = postRepository.getNewestPost(page, limit);
        var returnDto = entities
                .stream()
                .map(PostEntityToResponseDtoFactory::map)
                .collect(Collectors.toList());

        return RestResponse.ResponseBuilder
                .ok(returnDto, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET()
    @Path("/most-comments")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<PostResponseDto>> getMostCommentsPosts(@RestQuery("page") Integer page, @RestQuery("limit") Integer limit) {
        if (page == null || limit == null)
            throw new BadRequestException("page and limit must be set");
        var entities = postRepository.getMostCommentsPosts(page, limit);
        var returnDto = entities
                .stream()
                .map(PostEntityToResponseDtoFactory::map)
                .collect(Collectors.toList());

        return RestResponse.ResponseBuilder
                .ok(returnDto, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @GET()
    @Path("/most-votes")
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<List<PostResponseDto>> getMostVotesPosts(@RestQuery("page") Integer page, @RestQuery("limit") Integer limit) {
        if (page == null || limit == null)
            throw new BadRequestException("page and limit must be set");
        var entities = postRepository.getMostVotesPosts(page, limit);
        var returnDto = entities
                .stream()
                .map(PostEntityToResponseDtoFactory::map)
                .collect(Collectors.toList());

        return RestResponse.ResponseBuilder
                .ok(returnDto, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @POST
    @Path("/{id}/vote/up")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<PostVotingResponseDto> upVotePost(@RestPath("id") Long postId) {
        return votePost(postId, true);
    }

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @POST
    @Path("/{id}/vote/down")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<PostVotingResponseDto> downVotePost(@RestPath("id") Long postId) {
        return votePost(postId, false);
    }

    private RestResponse<PostVotingResponseDto> votePost(@RestPath("id") Long postId, boolean isUpvote) {
        var post = postRepository.findByIdOptional(postId);
        if (post.isEmpty()) {
            throw new NotFoundException("Post with '" + postId + "' could not be found!");
        }

        var user = userContextService.getCurrentUser();
        var postVotingModel = votingRepository.votePost(postId, user.id, isUpvote);

        return RestResponse.ResponseBuilder
                .ok(VotingEntityToResponseDtoFactory.mapPostVoting(postVotingModel), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<PostResponseDto> createPost(CreatePostDto post) {
        var user = userContextService.getCurrentUser();
        var entity = postRepository.createPostFor(user.id, user.userName, post.getText(), post.getColor().toString());
        return RestResponse.ResponseBuilder
                .ok(PostEntityToResponseDtoFactory.map(entity), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @Path("/{id}/vote")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<VotingResponseDto> getMyVote(@RestPath("id") Long postId) {
        var user = userContextService.getCurrentUser();
        var entity = votingRepository.getVoteOfPost(postId, user.id);
        return RestResponse.ResponseBuilder
                .ok(VotingEntityToResponseDtoFactory.map(entity, postId), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }


    @Path("/{id}/comments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<List<CommentResponseDto>> getComments(
            @RestPath("id") Long postId,
            @RestQuery("page") Integer page,
            @RestQuery("limit") Integer limit
    ) {
        if (page == null || limit == null)
            throw new BadRequestException("page and limit must be set");
        var entities = commentRepository.getCommentsOfPost(postId, page, limit);
        var returnDto = entities
                .stream()
                .map(CommentEntityToResponseDtoFactory::map)
                .collect(Collectors.toList());

        return RestResponse.ResponseBuilder
                .ok(returnDto, MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

    @Authenticated
    @SecurityRequirements({
            @SecurityRequirement(
                    name = "CookieAuth"
            )
    })
    @Path("/{id}/comments")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<CommentResponseDto> createComment(@RestPath("id") Long postId, CreateCommentDto dto) {
        var user = userContextService.getCurrentUser();
        var entity = commentRepository.createComment(postId, user.id, user.userName, dto.getText());
        postRepository.updateCommentCountByOne(postId);
        return RestResponse.ResponseBuilder
                .ok(CommentEntityToResponseDtoFactory.map(entity), MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
