package cmc.sole.android.Follow

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowingBinding

class FollowFollowingFragment: BaseFragment<FragmentFollowerFollowingBinding>(FragmentFollowerFollowingBinding::inflate) {

    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowListData>()

    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowingRv.adapter = followListRVAdapter
        binding.followFollowingRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        followListRVAdapter.setOnItemClickListener(object: FollowListRVAdapter.OnItemClickListener {
            override fun onItemClick(data: FollowListData, position: Int) {
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                    R.id.main_fl,
                    FollowUserFragment().apply {
                        arguments = Bundle().apply {
                            putString("profileImg", data.profileImgUrl)
                            putString("nickname", data.nickname)
                            putString("follower", data.follower)
                            putString("following", data.following)
                        }
                    }
                ).addToBackStack("followingUser").commit()
            }
        })

        // MEMO: DUMMY DATA
        followList.add(FollowListData("프로필", "닉네임 1", "1", "6", true))
        followList.add(FollowListData("프로필", "닉네임 2", "2", "2", true))
        followList.add(FollowListData("프로필", "닉네임 3", "3", "5", true))
        followList.add(FollowListData("프로필", "닉네임 4", "4", "4", true))
        followList.add(FollowListData("프로필", "닉네임 5", "5", "2", true))
        followList.add(FollowListData("프로필", "닉네임 6", "6", "22", true))
    }
}