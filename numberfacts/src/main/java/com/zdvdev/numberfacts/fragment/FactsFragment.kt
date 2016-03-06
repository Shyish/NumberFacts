package com.zdvdev.numberfacts.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.zdvdev.numberfacts.BuildConfig
import com.zdvdev.numberfacts.R
import com.zdvdev.numberfacts.common.validateMDDate
import com.zdvdev.numberfacts.datamodel.DataManager
import com.zdvdev.numberfacts.datamodel.model.FactType
import kotlinx.android.synthetic.main.facts_fragment_layout.*
import retrofit.RetrofitError
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * @author Shyish
 */
class FactsFragment : Fragment(), TextWatcher, RadioGroup.OnCheckedChangeListener,
        InputFilter {

    private var currentFact = FactType.TRIVIA

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.facts_fragment_layout, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFactButton.setOnClickListener { onGetFactButtonClicked() }
        shareButton.setOnClickListener { onShareButtonClicked() }

        typeRadioGroup.setOnCheckedChangeListener(this)
        typeRadioGroup.check(typeRadioGroup.getChildAt(0).id)

        numberEditText.filters = arrayOf<InputFilter>(this)
        numberEditText.addTextChangedListener(this)
    }

    internal fun onGetFactButtonClicked() {
        val number = numberEditText.text.toString()
        if (number.contains("/") && !validateMDDate(number)) {
            numberEditText.error = getString(R.string.error_date)
        } else {
            DataManager.getFact(currentFact, number)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ responseFact ->
                        if (!TextUtils.isEmpty(responseFact.text)) {
                            factTextView.text = responseFact.text
                            shareButton.visibility = View.VISIBLE
                        }
                    }, { throwable ->
                        factTextView.text = ""
                        shareButton.visibility = View.INVISIBLE
                        if (throwable is RetrofitError && throwable.kind == RetrofitError.Kind.NETWORK) {
                            Toast.makeText(activity, getString(R.string.error_internet), Toast.LENGTH_SHORT).show()
                        } else if (BuildConfig.DEBUG) {
                            numberEditText.error = throwable.message
                        }
                    })
        }
    }

    internal fun onShareButtonClicked() {
        val share = Intent(android.content.Intent.ACTION_SEND)

        share.type = "text/plain"
        share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Did you know that...?")
        share.putExtra(android.content.Intent.EXTRA_TEXT, factTextView.text)

        startActivity(Intent.createChooser(share, "Share this fact"))
    }

    /**
     * ********************
     * InputFilter METHOD *
     * ********************
     */
    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        for (i in start..end - 1) {
            val c = source[i]
            //Only allows digits or digits plus the '/' char if the current fact type is DATE
            if (!(Character.isDigit(c) || currentFact == FactType.DATE && c == '/')) {
                return ""
            }
        }
        return null
    }

    /**
     * *********************************
     * OnCheckedChangeListener METHODS *
     * *********************************
     */
    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        // Clear the edittext to avoid invalid chars in other fact types.
        if (numberEditText.text.toString().contains("/")) {
            factTextView.text = ""
            numberEditText.setText("")
        }
        val radioButtonID = group.checkedRadioButtonId
        val radioButton = group.findViewById(radioButtonID)
        currentFact = FactType.values()[group.indexOfChild(radioButton)]

        if (currentFact == FactType.DATE) {
            numberEditText.setHint(R.string.write_number_date_hint)
            numberEditText.inputType = InputType.TYPE_CLASS_TEXT
        } else {
            numberEditText.setHint(R.string.write_number_hint)
            numberEditText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    /**
     * *********************
     * TextWatcher METHODS *
     * *********************
     */
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
            getFactButton.setText(if (s.length > 0) R.string.get_fact else R.string.random_fact)

    override fun afterTextChanged(s: Editable) {
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

}