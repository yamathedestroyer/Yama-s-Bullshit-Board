package yamathedestroyer.horrorboard.audiostack

import android.content.Context
import android.media.MediaPlayer
import java.io.IOException

class MediaPlayFramework (val source : String, private val context: Context) {

    fun playback() : Int {
        val playbackAsset = context.assets.openFd(source)
        val mediaplayer = MediaPlayer()

        if (playbackAsset == null)
            return 0

        try {

            mediaplayer.setDataSource(playbackAsset.fileDescriptor, playbackAsset.startOffset, playbackAsset.length)
            playbackAsset.close()

            mediaplayer.prepare()
            mediaplayer.start()
            return 1
        } catch (e: IOException) {
            e.printStackTrace()
            return 0
        }
    }
}