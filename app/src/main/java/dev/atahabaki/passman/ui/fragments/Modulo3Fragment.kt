package dev.atahabaki.passman.ui.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dev.atahabaki.passman.R
import dev.atahabaki.passman.databinding.Modulo3FragmentBinding

class Modulo3Fragment : Fragment(R.layout.modulo3_fragment) {

    private var _binding: Modulo3FragmentBinding? = null
    private val binding get() = _binding!!

    enum class FormError {
        PLAIN_EMPTY, KEY_EMPTY, BOTH_EMPTY, LENGTH_NOT_EQUAL, NON, OTHER
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.modulo3ActionButton.setOnClickListener { modulo3Action()}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = Modulo3FragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun modulo3Action() {
        hideKeyboard()
        val key_length = binding.modulo3PlainTextField.editText?.text.toString().length
        val err = validate()
        if (err != FormError.NON) showSnackErr(err, key_length)
        else {
            val res = modulo3(binding.modulo3PlainTextField.editText?.text.toString().trim(),
                    binding.modulo3KeyTextField.editText?.text.toString().trim())!!
            copyToClipBoard(res)
            resetAll()
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    private fun resetAll() {
        binding.modulo3PlainTextField.editText?.setText("")
        binding.modulo3KeyTextField.editText?.setText("")
        binding.modulo3PlainTextField.isErrorEnabled = false
    }

    private fun copyToClipBoard(copyThis: String) {
        showSnackBar(R.string.modulo3_copied)
        val clipboardService = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("${R.string.package_name}.modulo3.result", copyThis)
        clipboardService.setPrimaryClip(clip)
    }

    private fun showSnackErr(formError: FormError, length: Int) {
        when (formError) {
            FormError.PLAIN_EMPTY -> showPlainErr(R.string.modulo3_plain_empty)
            FormError.KEY_EMPTY -> Snackbar.make(binding.root,R.string.modulo3_key_empty,Snackbar.LENGTH_LONG)
                    .setAction(R.string.modulo3_action) {
                        fillKey(length)
                    }.show()
            FormError.BOTH_EMPTY -> showSnackBar(R.string.modulo3_both_empty)
            FormError.LENGTH_NOT_EQUAL -> showSnackBar(R.string.modulo3_length_nt)
            FormError.OTHER -> showSnackBar(R.string.modulo3_unn_err)
            else -> showSnackBar(R.string.modulo3_unn_err)
        }
    }

    private fun showPlainErr(@StringRes resId: Int) {
        showSnackBar(resId)
        binding.modulo3PlainTextField.isErrorEnabled = true
        binding.modulo3PlainTextField.error = getString(resId)
    }

    private fun fillKey(length: Int) {
        binding.modulo3KeyTextField.editText?.setText(randomizeKey(length))
    }

    private fun randomizeKey(length: Int): String {
        val specialChars="!@#\$%^&*()_+-=1234567890,/;'\\[]{}|<>?`~"
        var i = 0
        var key = ""
        while (i < length) {
            key += specialChars[(0 until specialChars.length-1).random()]
            i++
        }
        return key
    }

    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.root,resId,Snackbar.LENGTH_LONG).show()
    }

    private fun validate(): FormError {
        val plain = binding.modulo3PlainTextField.editText?.text.toString().trim()
        val key = binding.modulo3KeyTextField.editText?.text.toString().trim()
        return if (plain.equals("") && key.equals(""))
            FormError.BOTH_EMPTY
        else if (plain.equals(""))
            FormError.PLAIN_EMPTY
        else if (key.equals(""))
            FormError.KEY_EMPTY
        else if (key.length !=  plain.length)
            FormError.LENGTH_NOT_EQUAL
        else if (key.length == plain.length)
            FormError.NON
        else FormError.OTHER
    }

    private fun modulo3(plain: String, key: String): String? {
        var ciphertext: String = ""
        if (plain.length == key.length) {
            var i = 0
            while (i < plain.length) {
                if (i % 123 == 0)
                    ciphertext += plain[i]
                if (i % 5 == 0)
                    ciphertext += key[i]
                if (i % 3 == 0)
                    ciphertext += key[i].toByte().toInt().toString()
                if (i % 2 == 0)
                    ciphertext += plain[i].toByte().toInt().toString()
                else
                    ciphertext += plain[i]
                i++
            }
        }
        return ciphertext
    }
}