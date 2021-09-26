package com.example.myapplicationtake100;

import Models.AddJobResponse;
import Models.JobTasksResponse;
import Models.Jobs;
import Models.RegisterNewAccountRequest;
import Models.RegisterNewAccountResponse;
import Models.RemoveNeed;
import Models.AddJobRequest;
import Models.addPersonRequest;
import Models.getGroupMembersResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    /*@GET("users")
    Call<Post> getPosts(
            @Path("username") String userName,
            @Path("password") String password);*/

    @GET("users/{username}/{password}")
        Call<Post> getPosts(@Path("username") String userName, @Path("password") String password);

    @GET("users/group/{groupId}/{personId}")
    Call<Jobs> getJobs(@Path("groupId") String groupId, @Path("personId") String personId);

    @GET("users/tasks/{groupId}/{personId}")
    Call<JobTasksResponse> getTasks(@Path("groupId") String groupId, @Path("personId") String personId);

    @GET("users/removeNeed/" +
            "{mongoId}")
    Call<RemoveNeed> getRemoveNeed(@Path("mongoId") String mongoId);

    @POST("users/addGroupMemember/")
    Call<addPersonRequest> addPerson(@Body addPersonRequest addPersonRequest);

    @GET ("users/getGroupMembers/{groupId}")
    Call<getGroupMembersResponse> getGroupMembers(@Path("groupId") String groupId);

    @POST ("user/markJobComplete/{mongoId}")
    Call<RemoveNeed> markJobComplete(@Path("groupId") String groupId);

    @POST ("users/registerNewAccount/")
    Call<RegisterNewAccountResponse> addNewAccount(@Body RegisterNewAccountRequest registerNewAccountRequest);

    @POST ("users/addNewJob/")
    Call<AddJobResponse> addNewJob(@Body AddJobRequest addJobModelRequest);
}
