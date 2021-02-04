package interview.android.tuner.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import interview.android.tuner.R
import interview.android.tuner.databinding.ViewCardItemLayoutBinding
import interview.android.tuner.view.presenter.PresenterCardItem

/* @desc generalized recycler view that will attach itself to base caller
 * @param mViewModel type provided to better determine the data and view associated with the adapter
 * @return bounded adapter to its inheritor */
abstract class RecyclerViewAdapter(private val mViewModel: ViewModel) : RecyclerView.Adapter<RecyclerViewAdapter.CardItemHolder>(){

    /* view holder used to inflate the card when needed */
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): CardItemHolder {
        val mCardInflater = LayoutInflater.from(parent.context)
        val mView = mCardInflater.inflate(R.layout.view_card_item_layout, parent, false)
        return CardItemHolder(mView)
    }

    /* binds data to ui element in each row, by design children will inherit and specify details */
    override fun onBindViewHolder(holder: CardItemHolder, position: Int) {
        holder.bind(position)
    }

    /* total number of card items based on the data set, by design children will inherit and specify count */
    override fun getItemCount(): Int {
        return DEFAULT_ITEM_COUNT
    }

    /* view holder used to store recycler view items */
    inner class CardItemHolder(private val mItemView: View): RecyclerView.ViewHolder(mItemView) {
        fun bind(position: Int) {
            val mBinding = DataBindingUtil.bind<ViewCardItemLayoutBinding>(mItemView)
            mBinding?.mPresenter =
                PresenterCardItem(
                    mItemView,
                    position
                )
            mBinding?.executePendingBindings()
        }
    }

    /* by definition a view count should be null (non existing), but to be type safe, use 0, to render no items*/
    companion object {
        const val DEFAULT_ITEM_COUNT: Int = 0
    }
}