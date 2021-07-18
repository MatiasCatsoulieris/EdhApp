package android.example.com.chatapp.util

import android.content.Context
import android.example.com.matsusmagic.R
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.widget.TextView

class CharsToIconUtil {

    companion object {
        val charsList = arrayListOf("{0}","{1}","{2}","{3}","{4}","{5}","{6}","{7}","{8}","{9}","{10}",
            "{11}","{12}","{13}","{14}","{15}","{16}","{17}","{18}","{19}","{20}","{2/W}","{2/U}",
            "{2/B}","{2/R}","{2/G}","{B}","{B/G}","{B/P}","{B/R}","{C}","{G}","{G/P}","{G/U}","{G/W}",
            "{R}","{R/G}","{R/P}","{R/W}","{S}","{U}","{U/B}","{U/P}","{U/R}","{W}","{W/B}","{W/P}",
            "{W/U}","{X}","{T}","{E}"
            )
        val iconsList = arrayListOf(
            R.drawable.mana_0,R.drawable.mana_1,R.drawable.mana_2,R.drawable.mana_3,
            R.drawable.mana_4,R.drawable.mana_5,R.drawable.mana_6,R.drawable.mana_7,
            R.drawable.mana_8,R.drawable.mana_9,R.drawable.mana_10,R.drawable.mana_11,
            R.drawable.mana_12,R.drawable.mana_13,R.drawable.mana_14,R.drawable.mana_15,
            R.drawable.mana_16,R.drawable.mana_17,R.drawable.mana_18,R.drawable.mana_19,
            R.drawable.mana_20,R.drawable.mana_2w,R.drawable.mana_2u,R.drawable.mana_2b,
            R.drawable.mana_2r,R.drawable.mana_2g,R.drawable.mana_b,R.drawable.mana_bg,
            R.drawable.mana_bp,R.drawable.mana_br,R.drawable.mana_c,R.drawable.mana_g,
            R.drawable.mana_gp,R.drawable.mana_gu,R.drawable.mana_gw,R.drawable.mana_r,
            R.drawable.mana_rg,R.drawable.mana_rp,R.drawable.mana_rw,R.drawable.mana_s,
            R.drawable.mana_u,R.drawable.mana_ub,R.drawable.mana_up,R.drawable.mana_ur,
            R.drawable.mana_w,R.drawable.mana_wb,R.drawable.mana_wp,R.drawable.mana_wu,
            R.drawable.mana_x,R.drawable.mana_t,R.drawable.mana_e

        )

        fun setIconInTxtView(txtView: TextView, text: String, context: Context) {

            if (charsList.size == iconsList.size) {
                val builder = SpannableStringBuilder(text)
                val spannable = Spannable.Factory.getInstance().newSpannable(text)

                for (i in 0 until charsList.size) {
                    var index = text.indexOf(charsList[i])
                    while(index >= 0) {
                        builder.setSpan(
                            ImageSpan(context, iconsList[i]), index, index + charsList[i].length,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                        index = text.indexOf(charsList[i], index + 1)
                    }
                }

                txtView.text = builder
            } else {

            }
        }
    }
}