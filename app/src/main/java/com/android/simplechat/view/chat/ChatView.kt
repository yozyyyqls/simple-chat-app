package com.android.simplechat.view.chat

import android.content.Context
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.android.simplechat.R
import com.android.simplechat.adapter.ChatMessageListAdapter
import com.android.simplechat.model.User
import com.android.simplechat.utils.dp2px

class ChatView constructor(
    context: Context,
    currentFriend: User,
    adapter: ChatMessageListAdapter
) : RelativeLayout(context) {
    private var toolbar: Toolbar? = null

    private var rvMessageFlow: MessageFlowView? = null

    private var llBottomContainer: LinearLayout? = null
    private var cvMessageInputBox: CardView? = null
    private var llMessageInputBoxInnerContainer: LinearLayout? = null
    private var etMessageInput: EditText? = null
    private var ivAttachFile: ImageView? = null
    private var ivAddPhoto: ImageView? = null
    private var ivSendMsg: ImageView? = null

    var messageText: String
        get() = etMessageInput?.text.toString()
        set(value) {
            etMessageInput!!.setText(value)
        }

    val messageBox: EditText?
        get() = etMessageInput

    val sendMessage: ImageView?
        get() = ivSendMsg

    val attachFile: ImageView?
        get() = ivAttachFile

    val addPhoto: ImageView?
        get() = ivAddPhoto

    init {
        // Header
        toolbar = LayoutInflater.from(context).inflate(R.layout.toolbar, null) as Toolbar
        toolbar!!.apply {
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                addRule(ALIGN_PARENT_TOP)
            }
            id = generateViewId()
            title = currentFriend.name ?: "Friend"
        }
        this.addView(toolbar)

        // Body
        rvMessageFlow = MessageFlowView(context, adapter).apply {
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT).apply {
                addRule(BELOW, toolbar!!.id)
            }
            id = generateViewId()
        }
        this.addView(rvMessageFlow)

        // Bottom
        ivAttachFile = ImageView(context).apply {
            setImageDrawable(context.getDrawable(R.drawable.ic_attach_file))
            layoutParams = LinearLayout.LayoutParams(dp2px(30f), dp2px(30f), 1f).apply {
                leftMargin = dp2px(8f)
                rightMargin = dp2px(4f)
                gravity = Gravity.CENTER_VERTICAL
            }
        }

        ivAddPhoto = ImageView(context).apply {
            setImageDrawable(context.getDrawable(R.drawable.ic_add_a_photo))
            layoutParams = LinearLayout.LayoutParams(dp2px(30f), dp2px(30f), 1f).apply {
                leftMargin = dp2px(4f)
                rightMargin = dp2px(8f)
                gravity = Gravity.CENTER_VERTICAL
            }
        }

        ivSendMsg = ImageView(context).apply {
            setImageDrawable(context.getDrawable(R.drawable.ic_send_msg))
            layoutParams = LinearLayout.LayoutParams(dp2px(50f), dp2px(50f), 2f).apply {
                leftMargin = dp2px(4f)
                rightMargin = dp2px(16f)
                topMargin = dp2px(8f)
                bottomMargin = dp2px(8f)
                gravity = Gravity.CENTER_HORIZONTAL
            }
        }

        etMessageInput = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 8f).apply {
                rightMargin = dp2px(4f)
            }
            background = null
        }

        llMessageInputBoxInnerContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT)
            addView(etMessageInput)
            addView(ivAttachFile)
            addView(ivAddPhoto)
        }

        cvMessageInputBox = CardView(context).apply {
            radius = 8f
            layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 8f).apply {
                leftMargin = dp2px(16f)
                rightMargin = dp2px(4f)
                topMargin = dp2px(8f)
                bottomMargin = dp2px(8f)
                gravity = Gravity.CENTER_VERTICAL
            }
            setCardBackgroundColor(context.getColor(R.color.white))
            addView(llMessageInputBoxInnerContainer)
        }

        llBottomContainer = LinearLayout(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, dp2px(56f)).apply {
                addRule(ALIGN_PARENT_BOTTOM)
            }
            orientation = LinearLayout.HORIZONTAL
            setBackgroundColor(context.getColor(R.color.colorPrimary))
            addView(cvMessageInputBox)
            addView(ivSendMsg)
        }
        this.addView(llBottomContainer)
    }
}
