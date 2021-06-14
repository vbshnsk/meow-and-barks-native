package mvvm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.meowsandbarks.R
import mvvm.data.CountryMap
import mvvm.data.PetLiveData
import mvvm.viewmodel.UserLoginFormViewModel

class CreateAccountFragment : Fragment() {

    private val form: UserLoginFormViewModel by activityViewModels()
    private lateinit var colors: List<Int?>
    private val citiesByCountry = CountryMap.instance

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        colors = listOf(
                context?.let { ContextCompat.getColor(it, R.color.sea_green) },
                context?.let { ContextCompat.getColor(it, R.color.light_green) },
                context?.let { ContextCompat.getColor(it, R.color.grass_green) })

        val view = inflater.inflate(R.layout.fragment_create_account, container, false)
        setupAddPetButton(view, mutableListOf())
        setupLocationForm(view)
        setupMonths(view)
        view.findViewById<Button>(R.id.add_tag_button).setOnClickListener {
            val v = AddTagBottomSheetFragment()
            v.show(requireActivity().supportFragmentManager, "add_tag")
        }

        view.findViewById<Button>(R.id.continue_button).setOnClickListener {
        }

        form.userData.observe(viewLifecycleOwner, Observer {
            setupTagList(it.profile.tags)
            setupAddPetButton(view, it.profile.pets)
        })


        return view
    }

    private fun setupMonths(view: View) {
        val selector = view.findViewById<AutoCompleteTextView>(R.id.month)
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.months, android.R.layout.simple_list_item_1)
        selector.setAdapter(adapter)
        selector.setOnItemClickListener { _, _, position, _ ->
            form.setBirthMonth((adapter.getItem(position) ?: "").toString())
        }
    }

    private fun setupLocationForm(view: View) {

        val countrySelector = view.findViewById<AutoCompleteTextView>(R.id.country)
        val countryAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, citiesByCountry.keys.toList())
        countrySelector.setAdapter(countryAdapter)

        val citySelector = view.findViewById<AutoCompleteTextView>(R.id.city)

        countrySelector.setOnItemClickListener { _, _, position, _ ->
            form.setLocationCountry(countryAdapter.getItem(position) ?: "")
        }

        citySelector.setOnItemClickListener { _, _, position, _ ->
            form.setLocationCity(countryAdapter.getItem(position) ?: "")
        }

        form.currentCountry.observe(viewLifecycleOwner, Observer {
            citySelector.clearListSelection()
            citySelector.text = null
            form.setLocationCity("")
            val cities = citiesByCountry.getOrElse(it) { listOf() }
            val cityAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, cities)
            citySelector.setAdapter(cityAdapter)
        })

    }

    private fun setupTagList(tags: MutableList<String>) {
        view?.let { view ->
            val list = view.findViewById<HorizontalScrollView>(R.id.list_view)[0] as LinearLayout
            list.removeAllViews()
            for (i in tags) {
                val v = layoutInflater.inflate(R.layout.tag, null)
                val tag = v.findViewById<Button>(R.id.tag_button)
                tag.setOnLongClickListener {
                    form.removeTag(i)
                    true
                }
                tag.text = i
                val col = colors.random()
                col?.let {
                    tag.setBackgroundColor(it)
                }
                list.addView(v)
            }
        }
    }

    private fun setupAddPetButton(view: View, pets: MutableList<PetLiveData>) {
        val photoList = view.findViewById<View>(R.id.pet_manage)
                .findViewById<HorizontalScrollView>(R.id.list_view)[0] as LinearLayout
        photoList.removeAllViews()
        val v = layoutInflater.inflate(R.layout.empty_image_add, null)

        for (pet in pets) {
            val image = layoutInflater.inflate(R.layout.image_base, null)
            image.findViewById<ImageView>(R.id.image)
                .setImageBitmap(pet.image)
            photoList.addView(image)
        }

        val petView = AddPetBottomSheetFragment()
        (v.findViewById<CardView>(R.id.add_card_button)).setOnClickListener {
            petView.show(requireActivity().supportFragmentManager, "add_pet")
        }
        photoList.addView(v)
    }

}