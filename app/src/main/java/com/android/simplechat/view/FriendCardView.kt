package com.android.simplechat.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import de.hdodenhof.circleimageview.CircleImageView

class FriendCardView constructor(
    context: Context
) : LinearLayout(context) {
    private var cvFriend: CardView? = null
    private var civUserProfile: CircleImageView? = null
    private var llNameContainer: LinearLayout? = null
    private var txName: TextView? = null
    private var txLatestMessage: TextView? = null

    var profile: Drawable? = null
        set(value) {
            field = value
        }

    var name: String = ""
        get() = field
        set(value) {
            field = value
        }

    var latestMsg: String = ""
        get() = field
        set(value) {
            field = value
        }

    init {
        this.orientation = HORIZONTAL
        this.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)

        cvFriend = CardView(context).apply {
            layoutParams = LayoutParams(MATCH_PARENT, 56).apply {
                leftMargin = 16
                rightMargin = 16
                topMargin = 4
                bottomMargin = 4
            }
            radius = 8f
        }
        this.addView(cvFriend)

        civUserProfile = CircleImageView(context).apply {
            setImageDrawable(profile!!)
        }
        this.addView(civUserProfile)

        txName = TextView(context).apply {
            text = name
        }
        txLatestMessage = TextView(context).apply {
            text = latestMsg
        }

        llNameContainer = LinearLayout(context).apply {
            orientation = VERTICAL
            layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            addView(txName)
            addView(txLatestMessage)
        }
        this.addView(llNameContainer)
    }
}
