package ir.nikapps.motionlayout_playground5

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import ir.nikapps.motionlayout_playground5.databinding.RvItemBinding

class RvAdapter(
    var list: MutableList<Single>,
    val onRemoveClick: () -> Unit,
    val onEditClick: () -> Unit
) : RecyclerView.Adapter<RvAdapter.LineListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineListViewHolder {
        val binding = RvItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LineListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LineListViewHolder, position: Int) =
        holder.generateView(list[position])


    override fun getItemCount(): Int = list.size

    inner class LineListViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ClickableViewAccessibility")
        fun generateView(lineModel: Single) {

            binding.mlLineListRvItemMotionLayout.setTransition(R.id.tran_lineList_reseter)
            binding.mlLineListRvItemMotionLayout.transitionToEnd()

            lineModel.isOpen = false

            binding.txtLineListRvItemTitle.text = lineModel.name
            binding.txtLineListRvItemTime.text = lineModel.name.toString()


            binding.mlLineListRvItemMotionLayout.setTransitionListener(object :
                MotionLayout.TransitionListener {
                override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

                override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

                override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {

                    lineModel.isOpen = (p1 == R.id.constSet_lineList_add ||
                            p1 == R.id.constSet_lineList_remove)
                }

                override fun onTransitionTrigger(
                    p0: MotionLayout?,
                    p1: Int,
                    p2: Boolean,
                    p3: Float
                ) {
                }
            })

            setAnimation(lineModel)

        }

        @SuppressLint("ClickableViewAccessibility")
        private fun setAnimation(lineModel: Single) {

            binding.mlLineListRvItemMotionLayout.setOnTouchListener { v, event ->
                val X_Edit = 130F
                val Y_Edit = 225F
                val X_Remove = 950F
                val Y_Remove = 225F
                var click = 0
                val diver = 40
                if (lineModel.isOpen)
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        Log.i(
                            "LOG33",
                            "generateView: LINE LIST RV ITEM : X= ${event.x} & Y= ${event.y} ACTION_DOWN"
                        )
                        Log.i(
                            "LOG33",
                            "generateView: LINE LIST RV ITEM : XX_EDIT= ${X_Edit} & YY_EDIT= ${Y_Edit}  ACTION_DOWN"
                        )
                        // TODO: 8/4/2021 DEBUG PENDING
                        if (event.x < X_Edit + diver && event.x > X_Edit - diver && event.y < Y_Edit + diver && event.y > Y_Edit - diver) {
                            Log.i("LOG33", "onBindViewHolder: CLICKED ON EDIT THERE :: ACTION_DOWN")
                            click = 1
                        }
                        if (event.x < X_Remove + diver && event.x > X_Remove - diver && event.y < Y_Remove + diver && event.y > Y_Remove - diver) {
                            Log.i(
                                "LOG33",
                                "onBindViewHolder: CLICKED ON REMOVE THERE :: ACTION_DOWN"
                            )
                            click = 2
                        }
                    }
                if (lineModel.isOpen)
                    if (event.action == MotionEvent.ACTION_UP) {
                        Log.i(
                            "LOG33",
                            "generateView: LINE LIST RV ITEM : X= ${event.x} & Y= ${event.y} ACTION_UP"
                        )
                        // TODO: 8/4/2021 DEBUG PENDING
                        if (event.x < X_Edit + diver && event.x > X_Edit - diver && event.y < Y_Edit + diver && event.y > Y_Edit - diver) {
                            Log.i("LOG33", "onBindViewHolder: CLICKED ON EDIT THERE :: ACTION_UP")
                            click = 1
                        }
                        if (event.x < X_Remove + diver && event.x > X_Remove - diver && event.y < Y_Remove + diver && event.y > Y_Remove - diver) {
                            Log.i("LOG33", "onBindViewHolder: CLICKED ON REMOVE THERE :: ACTION_UP")
                            click = 2
                        }
                        if (click == 2) {
                            Log.i("LOG33", "generateView: OFFICIALLY CLICKED ON REMOVE")
                            onRemoveClick.invoke()
                        } else if (click == 1) {
                            Log.i("LOG33", "generateView: OFFICIALLY CLICKED ON EDIT")
                            onEditClick.invoke()
                        }
                    }
                false
            }
        }
    }

    fun addSingleItem(lineModel: Single) {
        list.add(lineModel)
        this.notifyItemInserted(list.size - 1)
    }

    fun addAllItem(lineModels: List<Single>, position: Int) {
        list.addAll(position, lineModels)
        this.notifyItemRangeInserted(position, lineModels.size)
    }

    fun removeSingleItem(lineModel: Single) {
        var pos: Int = list.indexOf(lineModel)
        list.remove(lineModel)
        this.notifyItemRemoved(pos)
    }

    fun editSingleItem(lineModel: Single) {
        val pos = list.indexOf(lineModel)
        this.notifyItemChanged(pos)
    }

}