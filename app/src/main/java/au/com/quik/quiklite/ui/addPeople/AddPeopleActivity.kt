package au.com.quik.quiklite.ui.addPeople

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import au.com.quik.quiklite.R
import au.com.quik.quiklite.model.People
import au.com.quik.quiklite.ui.addPeople.AddPeopleViewModel.OnSave
import kotlinx.android.synthetic.main.activity_add_people.*

class AddPeopleActivity: AppCompatActivity() {

    private val viewModel = AddPeopleViewModel()

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, AddPeopleActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_people)
        init()
    }

    private fun init() {
        setEvents()
    }

    private fun setEvents() {
        activity_people_add_save_btn.setOnClickListener {
            viewModel.savePeople(buildPeople(), onSavePeople())
        }
    }

    private fun onSavePeople(): OnSave {
        return object : OnSave{
            override fun success() {
                Toast.makeText(this@AddPeopleActivity,
                        "saved",
                        Toast.LENGTH_SHORT).show()
                this@AddPeopleActivity.setResult(200)
                finish()
            }

            override fun failure(t: Throwable) {
                Toast.makeText(this@AddPeopleActivity,
                        "Check the internet and try again",
                        Toast.LENGTH_SHORT).show()
            }

            override fun invalidForm() {
                Toast.makeText(this@AddPeopleActivity,
                        "All fields are mandatory",
                        Toast.LENGTH_SHORT).show() //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

    private fun buildPeople(): People {
        return People(activity_people_add_name_txt.text.toString(),
                activity_people_add_age_txt.text.toString(),
                activity_people_add_rating_txt.text.toString(),
                activity_people_add_category_txt.text.toString())
    }
}