package com.example.myapplicationtake100;

import Models.AddJobRequest;
import Models.AddJobResponse;
import Models.AddTaskRequest;
import Models.GetTasksRequest;
import Models.JobTasksResponse;
import Models.Jobs;
import Models.MarkJobCompleteRequest;
import Models.MarkJobCompleteResponse;
import Models.RegisterNewAccountRequest;
import Models.RegisterNewAccountResponse;
import Models.RemoveNeed;
import Models.addPersonRequest;
import Models.addTaskResponse;
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

    @GET("users/jobs/{groupId}/{personId}")
    Call<Jobs> getJobs(@Path("groupId") String groupId, @Path("personId") String personId);

    @POST("users/getTask/")
    Call<JobTasksResponse> getTasks(@Body GetTasksRequest req);

    @GET("users/removeNeed/" +
            "{mongoId}")
    Call<RemoveNeed> getRemoveNeed(@Path("mongoId") String mongoId);

    @POST("users/addGroupMemember/")
    Call<addPersonRequest> addPerson(@Body addPersonRequest addPersonRequest);

    @GET ("users/getGroupMembers/{groupId}")
    Call<getGroupMembersResponse> getGroupMembers(@Path("groupId") String groupId);

    @POST ("user/markJobComplete/")
    Call<MarkJobCompleteResponse> markJobComplete(@Body MarkJobCompleteRequest markJobCompleteRequest);

    @POST ("users/registerNewAccount/")
    Call<RegisterNewAccountResponse> addNewAccount(@Body RegisterNewAccountRequest registerNewAccountRequest);

    @POST ("users/addJob/")
    Call<AddJobResponse> addNewJob(@Body AddJobRequest addJobModelRequest);

    @POST ("users/addTask/")
    Call<addTaskResponse> addNewTask(@Body AddTaskRequest addTaskRequest);
}
