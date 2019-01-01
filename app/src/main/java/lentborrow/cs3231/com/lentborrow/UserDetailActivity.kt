package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.book_customcell.view.*
import kotlinx.android.synthetic.main.review_customcell.view.*
import lentborrow.cs3231.com.lentborrow.controller.activity.ActivityMigrationController
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.user.User
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.database.user.review.Review
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.MessageController
import java.util.*

class UserDetailActivity : AppCompatActivity() {

    var userID = ""
    var lCon: LocalValueController? = null
    val uCon = UserController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        lCon = LocalValueController(this)
        val id = intent.getStringExtra("userID")
        if (id.isEmpty()) {
            userID = lCon!!.getID()
            if (!userID.isEmpty()) {
                uCon.getUserByID(userID, { user ->
                    userID = user.userID
                    setUser(user)
                    setReview(user)
                    hideReviewBox()
                }, { error ->
                    MessageController(this).showToast(error.message)
                })
            }
        } else {
            uCon.getUserByID(id, { user ->
                userID = user.userID
                setUser(user)
                setReview(user)
                if(user.userID == userID)
                    hideReviewBox()
            }, { error ->
                MessageController(this).showToast(error.message)
            })
        }
        reviewStarAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //not thing
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //not thing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    if (!p0.isEmpty()) {
                        val amount = p0.toString().toInt()
                        if (amount <= 5) {
                            var star = ""
                            for (i in 0 until amount) {
                                star += "★"
                            }
                            for (i in amount until 5) {
                                star += "☆"
                            }
                            newReviewStars.text = star
                        } else
                            newReviewStars.text = "★★★★★"

                    } else {
                        newReviewStars.text = "☆☆☆☆☆"
                    }
                } else {
                    newReviewStars.text = "☆☆☆☆☆"
                }
            }
        })
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    fun setUser(user: User) {
        //@TODO User review
        userName_userDetail.text = user.userName
        val bCon = BookController()
        bCon.getBooksByIDs(ArrayList(user.lending), { books ->
            setBooks(books)

            MessageController(this).showToast("Loaded")
        }, { error ->
            MessageController(this).showToast(error.message)
        })
    }

    fun createBookCell(book: Book): View {
        val factory = LayoutInflater.from(this)
        val myView = factory.inflate(R.layout.book_customcell, null)
        myView.bookName_bookCell.text = book.name
        myView.tradeType_bookCell.text = book.tradeType
        return myView
    }

    fun createReviewCell(review: Review): View {
        val factory = LayoutInflater.from(this)
        val myView = factory.inflate(R.layout.review_customcell, null)
        uCon.getUserByID(review.senderID, { user ->
            myView.userName_reviewCell.text = user.userName
            myView.reviewContent_reviewCell.text = review.senderID
            myView.userName_reviewCell.setOnClickListener {
                ActivityMigrationController()
                        .setUserDetail(this)
                        .pass("userID", review.senderID)
                        .go()
            }
        }, { error ->
            MessageController(this).showToast("Get user information failed.")
        })
        //myView.tradeType_bookCell.text = book.tradeType
        return myView
    }

    fun sendReview(view: View) {
        val content = reviewContent.text.toString()
        if (!reviewStarAmount.text.toString().isEmpty()) {
            var stars = reviewStarAmount.text.toString().toInt()
            if (stars < 0)
                stars = 0
            else if (stars < 5)
                stars = 5
            val calendar = Calendar.getInstance()
            val today = calendar.get(Calendar.YEAR).toString() +
                    "-" + calendar.get(Calendar.MONTH).toString() +
                    "-" + calendar.get(Calendar.DATE).toString()

            val review = Review(lCon!!.getID(), today, content, stars)
            uCon.createReview(userID, review)
        }

    }

    private fun setBooks(books: ArrayList<Book>) {
        val list = bookList_userDetail
        list.removeAllViews()
        for (book in books) {
            list.addView(createBookCell(book))
        }
    }

    private fun setReview(user: User) {
        val list = reviewList_userDetail
        list.removeAllViews()
        var totalStars = 0
        for (review in user.reviews) {
            list.addView(createReviewCell(review))
            totalStars += review.stars
            if (review.senderID == lCon!!.getID())
                hideReviewBox()
        }
        var reviewCount = user.reviews.count()
        if (0 < reviewCount)
            totalStars /= reviewCount
        var star = ""
        for (i in 0 until totalStars) {
            star += "★"
        }
        for (i in totalStars until 5) {
            star += "☆"
        }
        reviewStars.text = star
    }

    private fun hideReviewBox() {
        reviewBox.visibility = View.INVISIBLE
        MessageController(this).showToast("You already post review.")
    }
}

