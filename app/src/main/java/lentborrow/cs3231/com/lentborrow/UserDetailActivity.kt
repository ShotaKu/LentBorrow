package lentborrow.cs3231.com.lentborrow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.book_customcell.view.*
import lentborrow.cs3231.com.lentborrow.controller.database.book.Book
import lentborrow.cs3231.com.lentborrow.controller.database.book.BookController
import lentborrow.cs3231.com.lentborrow.controller.database.user.User
import lentborrow.cs3231.com.lentborrow.controller.database.user.UserController
import lentborrow.cs3231.com.lentborrow.controller.localValue.LocalValueController
import lentborrow.cs3231.com.lentborrow.generic.MessageController

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        val uCon = UserController()
        val lCon = LocalValueController(this)

        val id = intent.getStringExtra("userID")
        if (id.isEmpty()) {
            val userID = lCon.getID()
            if (!userID.isEmpty()) {
                uCon.getUserByID(userID, { user ->
                    run {
                        setUser(user)
                    }
                }, { error ->
                    run {
                        MessageController(this).showToast(error.message)
                    }
                })
            }
        } else {
            uCon.getUserByID(id, { user ->
                run {
                    setUser(user)
                }
            }, { error ->
                run {
                    MessageController(this).showToast(error.message)
                }
            })

        }
    }

    fun setUser(user: User) {
        //@TODO User review
        userName_userDetail.text = user.userName
        val bCon = BookController()
        bCon.getBooksByIDs(ArrayList(user.lending), { books ->
            run {
                setBooks(books)
                setReview(user);
                MessageController(this).showToast("Loaded")
            }
        }, { error ->
            run {
                MessageController(this).showToast(error.message)
            }
        })
    }

    fun createCell(book: Book): View {
        val factory = LayoutInflater.from(this)
        val myView = factory.inflate(R.layout.book_customcell, null)
        myView.bookName_bookCell.text = book.name
        myView.tradeType_bookCell.text = book.tradeType
        return myView
    }

    private fun setBooks(books: ArrayList<Book>) {
        for (book in books) {
            bookList_userDetail.addView(createCell(book))
        }
    }

    private fun setReview(user: User) {
        //@TODO

    }

    private fun Load() {

    }
}
