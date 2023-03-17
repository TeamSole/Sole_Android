package cmc.sole.android.Follow

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cmc.sole.android.MainActivity
import cmc.sole.android.R
import cmc.sole.android.Utils.BaseFragment
import cmc.sole.android.databinding.FragmentFollowerFollowerBinding

class FollowFollowerFragment: BaseFragment<FragmentFollowerFollowerBinding>(FragmentFollowerFollowerBinding::inflate) {

    lateinit var followListRVAdapter: FollowListRVAdapter
    private var followList = ArrayList<FollowListData>()

    override fun initAfterBinding() {
        initAdapter()
    }

    private fun initAdapter() {
        followListRVAdapter = FollowListRVAdapter(followList)
        binding.followFollowerRv.adapter = followListRVAdapter
        binding.followFollowerRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

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
                ).addToBackStack("followerUser").commit()
            }
        })

        // MEMO: DUMMY DATA
        followList.add(FollowListData("프로필", "지미", "1", "2", true))
        followList.add(FollowListData("프로필", "제이", "2", "2", false))
        followList.add(FollowListData("프로필", "너구리", "3", "1", false))
        followList.add(FollowListData("프로필", "밍밍", "2", "1", true))
    }
}