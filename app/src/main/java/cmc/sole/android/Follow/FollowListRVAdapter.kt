package cmc.sole.android.Follow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cmc.sole.android.databinding.ItemFollowListBinding
import com.bumptech.glide.Glide

class FollowListRVAdapter(private val followList: ArrayList<FollowUserDataResult>): RecyclerView.Adapter<FollowListRVAdapter.ViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(data: FollowUserDataResult, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemFollowListBinding = ItemFollowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(followList[position], position)
        }
        // TODO: 팔로우 및 언팔로우 API 연동
        holder.binding.itemFollowFollowBtn.setOnClickListener {
            itemClickListener.onItemClick(followList[position], position)
            holder.binding.itemFollowFollowBtn.visibility = View.GONE
            holder.binding.itemFollowFollowingBtn.visibility = View.VISIBLE
        }
        holder.binding.itemFollowFollowingBtn.setOnClickListener {
            itemClickListener.onItemClick(followList[position], position)
            holder.binding.itemFollowFollowBtn.visibility = View.VISIBLE
            holder.binding.itemFollowFollowingBtn.visibility = View.GONE
        }

        holder.bind(followList[position])
    }

    override fun getItemCount(): Int = followList.size

    inner class ViewHolder(val binding: ItemFollowListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(followList: FollowUserDataResult) {
            Glide.with(binding.root.context).load(followList.member.profileImgUrl).into(binding.itemFollowListProfileIv)
            binding.itemFollowListNicknameTv.text = followList.member.nickname
            binding.itemFollowListFollowerTv.text = followList.followerCount.toString() + " 팔로워"
            binding.itemFollowListFollowingTv.text = followList.followingCount.toString() + " 팔로잉"

            if (followList.followStatus == "FOLLOWER") {
                binding.itemFollowFollowingBtn.visibility = View.VISIBLE
                binding.itemFollowFollowBtn.visibility = View.GONE
            } else {
                binding.itemFollowFollowingBtn.visibility = View.GONE
                binding.itemFollowFollowBtn.visibility = View.VISIBLE
            }
        }
    }

    fun addItem(item: FollowUserDataResult) {
        followList.add(item)
        this.notifyDataSetChanged()
    }

    fun addAllItems(items: ArrayList<FollowUserDataResult>) {
        followList.addAll(items)
        this.notifyDataSetChanged()
    }
}