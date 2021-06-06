package mvvm.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.meowsandbarks.R
import mvvm.viewmodel.UserViewModel

class CreateAccountFragment : Fragment() {

    private val form: UserViewModel by activityViewModels()
    private lateinit var colors: List<Int?>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        colors = listOf(
                context?.let { ContextCompat.getColor(it, R.color.sea_green) },
                context?.let { ContextCompat.getColor(it, R.color.light_blue) },
                context?.let { ContextCompat.getColor(it, R.color.light_green) },
                context?.let { ContextCompat.getColor(it, R.color.grass_green) })

        val view = inflater.inflate(R.layout.fragment_create_account, container, false)
        setupAddPetButton(view)
        view.findViewById<Button>(R.id.add_tag_button).setOnClickListener {
            val v = AddTagBottomSheetFragment();
            v.show(requireActivity().supportFragmentManager, "add_tag")
        }

        form.userData.observe(viewLifecycleOwner, Observer {
            setupTagList(it.profile.tags)
        })
        
        return view
    }

    private fun setupTagList(tags: MutableList<String>) {
        Log.i("test", "called")
        view?.let { view ->
            val list = view.findViewById<HorizontalScrollView>(R.id.list_view)[0] as LinearLayout
            Log.i("test", "entered")
            Log.i("test", tags.toString())
            list.removeAllViews()
            for (i in tags) {
                val v = layoutInflater.inflate(R.layout.tag, null)
                val tag = v.findViewById<Button>(R.id.tag_button)
                tag.text = i
                val col = colors.random()
                col?.let {
                    tag.setBackgroundColor(it)
                }
                list.addView(v)
            }
            Log.i("test", "all done")
        }
    }

    private fun setupAddPetButton(view: View) {
        val photoList = view.findViewById<View>(R.id.pet_manage)
                .findViewById<HorizontalScrollView>(R.id.list_view)[0] as LinearLayout
        val v = layoutInflater.inflate(R.layout.empty_image_add, null)

        val petView = AddPetBottomSheetFragment()
        (v.findViewById<CardView>(R.id.bg_card)).setOnClickListener {
            petView.show(requireActivity().supportFragmentManager, "add_pet")
        }
        photoList.addView(v)
    }

}