package com.and.and_labo4.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.and.and_labo4.R
import com.and.and_labo4.models.NoteAndSchedule
import com.and.and_labo4.models.State
import com.and.and_labo4.models.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * Adapter of Notes and schedule for a recycler view
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    /**
     * Displayed list
     */
    var items = listOf<NoteAndSchedule>()
        set(value) {
            val diffCallback = NotesDiffCallback(items, value)
            val diffItems = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffItems.dispatchUpdatesTo(this)
        }

    /**
     * Sort the displayed notes by creation date.
      */
    fun sortByCreationDate() {
        items = items.sortedBy { it.note.creationDate }
    }

    /**
     * Sort the displayed notes by ETA.
     */
    fun sortByETA() {
        items = items.sortedWith(compareBy<NoteAndSchedule> {it.schedule == null }.thenBy { it.schedule?.date })

    }

    /**
     * Returns list size.
     */
    override fun getItemCount() = items.size

    /**
     * Return the type of a given note 1 = Simple, 2 = Scheduled.
     */
    override fun getItemViewType(position: Int): Int {
        if (items[position].schedule != null) return SCHEDULED
        else return SIMPLE
    }

    /**
     * Simple companion for type storing.
     */
    companion object {
        private const val SIMPLE = 1
        private const val SCHEDULED = 2
    }

    /**
     * Layout inflate
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            SIMPLE -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_simple_note, parent, false)
            )
            /* SCHEDULED */
            else -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_scheduled_note, parent, false))
        }
    }

    /**
     * Binding to layout
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Private class responsible to bind object to layout
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        // Getting ref on common info (imageView for type, textView for title, textView for text).
        private val commonInfoSimple = view.findViewById<View>(R.id.simple_note_info)
        private val commonInfoScheduled = view.findViewById<View>(R.id.scheduled_note_info)

        // Specific field of scheduled note
        private val clockImage   = view.findViewById<ImageView>(R.id.scheduled_note_clock)
        private val deadlineText = view.findViewById<TextView>(R.id.scheduled_note_deadline)

        //private val nameAves = view.findViewById<TextView>(R.id.list_item_aves_name)
        fun bind(noteAndSchedule: NoteAndSchedule) {

            // If simple note
            if (noteAndSchedule.schedule == null) {
                bindCommon(noteAndSchedule, commonInfoSimple)
            }
            // else note with schedule
            else {
                bindCommon(noteAndSchedule, commonInfoScheduled)

                val now = Date()

                // Changing clock color if late + text update under clock
                // Past delay:
                if(noteAndSchedule.schedule.date.timeInMillis - now.time < 0 ) {
                    clockImage.setColorFilter(clockImage.context.getColor(R.color.red))
                    deadlineText.text = deadlineText.context.getText(R.string.late)
                }
                // Incoming delay
                else {
                    val startDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(now.time), ZoneId.systemDefault())
                    val endDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(noteAndSchedule.schedule.date.timeInMillis), ZoneId.systemDefault())
                    clockImage.clearColorFilter()
                    when {
                        ChronoUnit.MONTHS.between(startDate, endDate) > 1 -> {
                            deadlineText.text = deadlineText.context.getString(R.string.months, ChronoUnit.MONTHS.between(startDate, endDate))
                        }
                        ChronoUnit.WEEKS.between(startDate, endDate) > 1 -> {
                            deadlineText.text = deadlineText.context.getString(R.string.weeks, ChronoUnit.WEEKS.between(startDate, endDate))
                        }
                        else -> {
                            deadlineText.text = deadlineText.context.getString(R.string.days, ChronoUnit.DAYS.between(startDate, endDate))
                        }
                    }
                }
            }
        }

        /**
         * Both notes layout (phote and pad) include the same layout for note info,
         * so we use a function to map info independently from layout
         */
        private fun bindCommon(noteAndSchedule: NoteAndSchedule, view: View) {
            val typeIcon = view.findViewById<ImageView>(R.id.note_type) // Note icon
            val title = view.findViewById<TextView>(R.id.note_title)    // Note title
            val text = view.findViewById<TextView>(R.id.note_text)      // Note text

            // Set icon from type
            when(noteAndSchedule.note.type) {
                Type.TODO     -> typeIcon.setImageResource(R.drawable.todo)
                Type.SHOPPING -> typeIcon.setImageResource(R.drawable.shopping)
                Type.WORK     -> typeIcon.setImageResource(R.drawable.work)
                Type.FAMILY   -> typeIcon.setImageResource(R.drawable.family)
                else -> { /* Keep default image */ }
            }

            // Set icon color from state
            when(noteAndSchedule.note.state) {
                State.IN_PROGRESS -> typeIcon.setColorFilter(view.context.getColor(R.color.blue))
                State.DONE -> typeIcon.setColorFilter(view.context.getColor(R.color.green))
                else -> { /* Keep default color */ }
            }

            // Set title and text
            title.text = noteAndSchedule.note.title
            text.text = noteAndSchedule.note.text
        }
    }
}

/**
 * Callback class to compare adapter items
 * @author Berney Alec
 * @author Forestier Quentin
 * @author Herzig Melvyn
 */
class NotesDiffCallback(private val oldList: List<NoteAndSchedule>, private val newList: List<NoteAndSchedule>) : DiffUtil.Callback() {

    /**
     * Gets current displayed list size
     */
    override fun getOldListSize() = oldList.size

    /**
     * Gets futur displayed list size
     */
    override fun getNewListSize() = newList.size

    /**
     * Notes are considered same when they have the same id.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].note.noteId == newList[newItemPosition].note.noteId
    }

    /**
     * Notes are considered having the same content when title and text are equal.
     */
    override fun areContentsTheSame(oldItemPosition : Int, newItemPosition : Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class &&
               old.note.text == new.note.text &&
               old.note.title == new.note.title
    }
}
