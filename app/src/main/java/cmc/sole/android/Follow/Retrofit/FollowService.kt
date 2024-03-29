package cmc.sole.android.Follow.Retrofit

import android.util.Log
import cmc.sole.android.DefaultResponse
import cmc.sole.android.Follow.FollowCourseResponse
import cmc.sole.android.Follow.FollowListResponse
import cmc.sole.android.Follow.FollowUserInfoResponse
import cmc.sole.android.Scrap.Retrofit.ScrapFolderDataResponse
import com.example.geeksasaeng.Utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowService {
    var followService = NetworkModule.getInstance()?.create(FollowRetrofitInterfaces::class.java)

    private lateinit var followCourseView: FollowCourseView
    private lateinit var followListView: FollowListView
    private lateinit var followUnfollowView: FollowUnfollowView
    private lateinit var followUserInfoView: FollowUserInfoView

    fun setFollowCourseView(followCourseView: FollowCourseView) {
        this.followCourseView = followCourseView
    }
    fun setFollowListView(followerListView: FollowListView) {
        this.followListView = followerListView
    }
    fun setFollowUnfollowView(followUnfollowView: FollowUnfollowView) {
        this.followUnfollowView = followUnfollowView
    }
    fun setFollowUserInfoView(followUserInfoView: FollowUserInfoView) {
        this.followUserInfoView = followUserInfoView
    }

    fun getFollowCourse() {
        followService?.getFollowCourse()?.enqueue(object: Callback<FollowCourseResponse> {
            override fun onResponse(
                call: Call<FollowCourseResponse>,
                response: Response<FollowCourseResponse>
            ) {
                if (response.code() == 200) {
                    val followCourseResponse = response.body()
                    if (followCourseResponse?.success == true) {
                        followCourseView.followCourseSuccessView(followCourseResponse.data)
                    } else {
                        followCourseView.followCourseFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<FollowCourseResponse>, t: Throwable) {
                Log.e("FOLLOW-SERVICE", "FOLLOW-SERVICE-GET-FOLLOW-COURSE-FAILURE", t)
            }
        })
    }

    fun getFollowerList() {
        followService?.getFollowersList()?.enqueue(object: Callback<FollowListResponse> {
            override fun onResponse(
                call: Call<FollowListResponse>,
                response: Response<FollowListResponse>
            ) {
                if (response.code() == 200) {
                    val followerListResponse = response.body()
                    if (followerListResponse?.success == true) {
                        followListView.followListSuccessView(followerListResponse.data)
                    } else {
                        followListView.followListFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<FollowListResponse>, t: Throwable) {
                Log.e("FOLLOW-SERVICE", "FOLLOW-SERVICE-GET-FOLLOWER-FAILURE", t)
            }
        })
    }

    fun getFollowingList() {
        followService?.getFollowingsList()?.enqueue(object: Callback<FollowListResponse> {
            override fun onResponse(
                call: Call<FollowListResponse>,
                response: Response<FollowListResponse>
            ) {
                if (response.code() == 200) {
                    val followerListResponse = response.body()
                    if (followerListResponse?.success == true) {
                        followListView.followListSuccessView(followerListResponse.data)
                    } else {
                        followListView.followListFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<FollowListResponse>, t: Throwable) {
                Log.e("FOLLOW-SERVICE", "FOLLOW-SERVICE-GET-FOLLOWING-FAILURE", t)
            }
        })
    }

    fun followUnfollow(toMemberId: Int) {
        followService?.followUnfollow(toMemberId)?.enqueue(object: Callback<DefaultResponse> {
            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                if (response.code() == 200) {
                    val followUnfollowResponse = response.body()
                    if (followUnfollowResponse?.success == true) {
                        followUnfollowView.followUnfollowSuccessView()
                    } else {
                        followUnfollowView.followUnfollowFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("FOLLOW-SERVICE", "FOLLOW-SERVICE-FOLLOW-UNFOLLOW-FAILURE", t)
            }
        })
    }

    fun getFollowUserInfo(followInfoMemberSocialId: String, courseId: Int?) {
        followService?.getFollowUserInfo(followInfoMemberSocialId, courseId)?.enqueue(object: Callback<FollowUserInfoResponse> {
            override fun onResponse(
                call: Call<FollowUserInfoResponse>,
                response: Response<FollowUserInfoResponse>
            ) {
                if (response.code() == 200) {
                    val followUserInfoResponse = response.body()
                    if (followUserInfoResponse?.success == true) {
                        followUserInfoView.followUserInfoSuccessView(followUserInfoResponse.data)
                    } else {
                        followUserInfoView.followUserInfoFailureView()
                    }
                }
            }
            override fun onFailure(call: Call<FollowUserInfoResponse>, t: Throwable) {
                Log.e("FOLLOW-SERVICE", "FOLLOW-SERVICE-GET-FOLLOW-INFO-FAILURE", t)
            }
        })
    }
}