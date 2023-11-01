package com.luis.beerapplication.framework.presentation.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.luis.beerapplication.R
import com.luis.beerapplication.databinding.FragmentDetailBinding
import com.luis.beerapplication.framework.presentation.activity.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.binding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_detail, container, false
            )

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainViewModel = this.mainViewModel
        binding.item = this.mainViewModel.item

        (requireActivity() as AppCompatActivity).supportActionBar?.title = binding.item?.name
        setHasOptionsMenu(true)

        this.setupAddFavorite()
        this.setupItemPicture()
        this.setupSetOne()
        this.setupObservers()
    }

    private fun setupAddFavorite() {
        binding.item?.let {
            if (it.isFavorite) {
                binding.addToFavorite.backgroundTintList =
                    requireContext().getColorStateList(R.color.colorPrimary)

            } else {
                binding.addToFavorite.backgroundTintList =
                    requireContext().getColorStateList(R.color.colorWhite)
            }
        }
    }

    private fun setupItemPicture() {
        val userAgent =
            "Mozilla/5.0 (Linux; Android 11) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.181 Mobile Safari/537.36"

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.beer_dummy)
            .error(R.drawable.beer_dummy)

        binding.item?.let {
            val glideUrl = GlideUrl(
                it.photo ?: "Fail",
                LazyHeaders.Builder().addHeader("User-Agent", userAgent).build()
            )

            Glide.with(requireContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(glideUrl)
                .fitCenter()
                .timeout(6000)
                .into(binding.itemPicture)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupSetOne() {
        binding.set1.titleDescription.text =
            "${getString(R.string.foodPairing)}${binding.item?.food_pairing?.size}"

        var strSetOne = String()
        binding.item?.food_pairing?.let {
            for (food in it) {
                strSetOne = "$strSetOne${food.trim()}\n"
            }
        }

        binding.set1.expandableText.text = strSetOne

    }

    private fun setupObservers() {
        mainViewModel.eventAddFavorite.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.item?.isFavorite == true) {
                    binding.item
                    binding.addToFavorite.backgroundTintList =
                        requireContext().getColorStateList(R.color.colorPrimary)

                    Snackbar.make(requireView(), R.string.addedFavorite, Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    binding.addToFavorite.backgroundTintList =
                        requireContext().getColorStateList(R.color.colorWhite)

                    Snackbar.make(requireView(), R.string.removeFavorite, Snackbar.LENGTH_SHORT)
                        .show()
                }

                mainViewModel.eventAddFavoriteComplete()
            }
        }
    }

    private fun getSharedIntend(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(
                getString(
                    R.string.share_text,
                    binding.item?.name,
                    binding.item?.tagline,
                    binding.item?.abv
                )
            )
            .setType("text/plain")
            .intent
    }

    private fun shareSuccess() {
        startActivity(getSharedIntend())
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        if (null == getSharedIntend().resolveActivity(requireActivity().packageManager)) {
            //No se pudo llamar, entonces hacemos invisible el boton de compartir!
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}