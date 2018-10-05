package au.com.quik.quiklite.ui.people

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.People
import au.com.quik.quiklite.ui.addPeople.AddPeopleActivity
import au.com.quik.quiklite.ui.addPeople.AddPeopleViewModel
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.android.synthetic.main.fragment_people.view.*

class PeopleFragment : Fragment() {

    companion object {
        const val TAG = "PeopleFragment"
    }

    lateinit var mainView : View
    var viewModel = PeopleViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_people, container, false)
        init()

        return mainView
    }

    private fun init() {
        setEvents()
        getPeople()
    }

    private fun setEvents() {
        mainView.fragment_people_swipe_refresh.setOnRefreshListener {
            mainView.fragment_people_swipe_refresh.isRefreshing = true
            getPeople()
        }

        mainView.fragment_shift_fab_add_people.setOnClickListener {
            val intent = AddPeopleActivity.newIntent(this@PeopleFragment.context!!)
            startActivityForResult(intent, 200)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 200){
            getPeople()
        }
    }

    private fun getPeople() {
        viewModel.getPeople(onGetPeople())
    }

    private fun setList(people: MutableList<People>) {
        with(mainView.fragment_shift_rv){
            layoutManager = LinearLayoutManager(context)
            adapter = PeopleAdapter(context!!,
                    people,
                    onClickPeople())
        }
    }

    private fun onClickPeople(): PeopleAdapter.OnClick {
        return object : PeopleAdapter.OnClick{
            override fun personClicked(person: People) {
                viewModel.removePeople(person.id, onDeletePeople())
            }

        }
    }

    private fun onDeletePeople(): PeopleViewModel.OnDelete {
        return object : PeopleViewModel.OnDelete{
            override fun success() {
                getPeople()
            }

            override fun failure(t: Throwable) {
                showToastError()
            }

        }
    }

    private fun onGetPeople(): PeopleViewModel.OnGetPeople {
        return object : PeopleViewModel.OnGetPeople{
            override fun success(people: MutableList<People>) {
                setList(people)

                if (fragment_people_swipe_refresh != null){
                    fragment_people_swipe_refresh.isRefreshing = false
                }
            }

            override fun failure(t: Throwable) {
                showToastError()
            }

        }
    }

    fun showToastError() {
        Toast.makeText(this@PeopleFragment.activity,
                "Something happend wrong =(",
                Toast.LENGTH_SHORT).show()
    }
}