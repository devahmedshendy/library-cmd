package local.shendy.services;

// MARK: Import Statements

import local.shendy.Utils;
import local.shendy.models.Book;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

// MARK: Class Definition

/**
 * It manage reading to and writing from buffer in once place.
 * It fits a buffer with size equals to recordSize contants.
 * <p>
 * It manage read-from/write-to binary file through SeekableByteChannel.
 * It manage reading Book object to fill all the buffer bytes with values
 * It manage filling Book object with all values from the buffer bytes.
 */
public class RecordBufferManager {

    // MARK: Buffer related constants

    public static final int ID_SIZE = 2;
    public static final int TITLE_SIZE = 50;
    public static final int AUTHOR_SIZE = 50;
    public static final int DESCRIPTION_SIZE = 255;
    public static final int RECORD_SIZE = ID_SIZE + TITLE_SIZE + AUTHOR_SIZE + DESCRIPTION_SIZE;

    private final int ID_POSITION = 0;
    private final int TITLE_POSITION = ID_POSITION + RecordBufferManager.ID_SIZE;
    private final int AUTHOR_POSITION = TITLE_POSITION + RecordBufferManager.TITLE_SIZE;
    private final int DESCRIPTION_POSITION = AUTHOR_POSITION + RecordBufferManager.AUTHOR_SIZE;

    // MARK: Properties

    private final String CHARSET_NAME = "UTF-8";
    private ByteBuffer recordBuffer;
    private MODE mode;

    /**
     * In constructs an instance of the class with:
     * - ByteBuffer (recordBuffer) with size RECORD_SIZE
     * - Clears the buffer (position=0, limit=capacity)
     * - Sets the initial mode for the buffer as READ
     */
    public RecordBufferManager() {
        this.recordBuffer = ByteBuffer.allocate(RECORD_SIZE);
        this.recordBuffer.clear();
        this.setReadMode();
    }

    // MARK: Getters

    public ByteBuffer getRecordBuffer() {
        return this.recordBuffer;
    }

    // MARK: Switch to Read/Write Operation

    /**
     * It sets mode to READ if mode is WRITE.
     * It clears the buffer (position=0, limit=capacity)
     */
    public void beingReading() {
        if (this.isWritingMode()) {
            this.setReadMode();
        }

        this.recordBuffer.clear();
    }

    /**
     * It just sets recordBuffer's position to 0
     */
    public void finishReading() {
        this.recordBuffer.rewind();
    }

    /**
     * It sets mode to WRITE if mode is READ.
     * It clears the buffer (position=0, limit=capacity)
     */
    public void beginWriting() {
        if (this.isReadingMode()) {
            this.setWriteMode();
        }

        this.recordBuffer.clear();
    }

    /**
     * It just sets recordBuffer's position to 0
     */
    public void finishWriting() {
        this.recordBuffer.rewind();
    }

    // MARK: Method for Reading from or Writing to recordBuffer

    /**
     * It reads one record from the binary file.
     * <p>
     * It reads from the binary file through ByteChannel, then writes
     * in the recordBuffer starting from position=0 up to its capacity.
     */
    public void readFromChannel(SeekableByteChannel byteChannel) {
        try {
            this.beingReading();
            byteChannel.read(this.recordBuffer);
            this.finishReading();

        } catch (IOException e) {
            Utils.printCannotReadDatabaseFile();
            Utils.println(e);
        }
    }

    /**
     * It writes a one full-size record in the binary file.
     * <p>
     * It sets position to where the write should happen,
     * <p>
     * It reads from the recordBuffer file through ByteChannel, and write
     * in the buffer starting from position=0 up to its capacity.
     */
    public void writeToChannel(int id, SeekableByteChannel byteChannel) {
        try {
            // This ensures that position is the beginning of a new record
            // One record size is 357
            // starting position for each record = 375 * (id - 1)
            //   when id=1 => position = 0 which is for first record
            //   when id=2 => position = 375 which is for second record
            //      first record size will be from position 0 to position (375 - 1)
            long updatePosition = RECORD_SIZE * (id - 1);
            byteChannel.position(updatePosition);

            this.beingReading();
            byteChannel.write(this.recordBuffer);
            this.finishReading();

            // This ensure position of the ByteChannel is back to 0 for any further write opertion.
            byteChannel.position(0);

        } catch (IOException e) {
            Utils.printCannotWriteToDatabaseFile();
            Utils.println(e);
        }
    }

    /**
     * It takes every field of Book object and writes it in the
     * buffer.
     * <p>
     * Each field's position will be at the end of the previous fields' position.
     */
    public void readFromBook(Book book) {
        this.beginWriting();
        recordBuffer.position(this.ID_POSITION);
        recordBuffer.put(this.getBytes(book.getId().toString()));

        recordBuffer.position(this.TITLE_POSITION);
        recordBuffer.put(this.getBytes(book.getTitle()));

        recordBuffer.position(this.AUTHOR_POSITION);
        recordBuffer.put(this.getBytes(book.getAuthor()));

        recordBuffer.position(this.DESCRIPTION_POSITION);
        recordBuffer.put(this.getBytes(book.getDescription()));
        this.finishWriting();
    }

    /**
     * Extrac every field from the buffer, and set it in Book object.
     */
    public Book writeToBook() {
        this.beingReading();

        int id = 0;
        try {
            id = Integer.parseInt(getIdValueFromRecordBuffer());
        } catch (NumberFormatException e) {
            Utils.println(e);
        }

        String title = getTitleValueFromRecordBuffer();
        String author = getAuthorValueFromRecordBuffer();
        String description = getDescriptionValueFromRecordBuffer();

        this.finishReading();

        return new Book(id, title, author, description);
    }

    // MARK: Methods for extracting fields values (id, title, etc...) from recordBuffer

    public String getIdValueFromRecordBuffer() {
        return getFieldValueFromRecordBuffer(ID_SIZE).trim();
    }

    public String getTitleValueFromRecordBuffer() {
        return getFieldValueFromRecordBuffer(TITLE_SIZE);
    }

    public String getAuthorValueFromRecordBuffer() {
        return getFieldValueFromRecordBuffer(AUTHOR_SIZE);
    }

    public String getDescriptionValueFromRecordBuffer() {
        return getFieldValueFromRecordBuffer(DESCRIPTION_SIZE);
    }

    /**
     * It will read array of bytes from the buffer based on the passed fieldSize,
     * then it convert this array of bytes to string.
     */
    private String getFieldValueFromRecordBuffer(int fieldSize) {
        byte[] fieldBytes = new byte[fieldSize];
        this.recordBuffer.get(fieldBytes, 0, fieldSize - 1);

        return this.convertBytesToString(fieldBytes);
    }

    // MARK: Switching Mode related Private Methods

    private void setReadMode() {
        this.mode = MODE.READ;
    }

    private void setWriteMode() {
        this.mode = MODE.WRITE;
    }

    private boolean isWritingMode() {
        return this.mode == MODE.WRITE;
    }

    private boolean isReadingMode() {
        return this.mode == MODE.READ;
    }

    // MARK: Enum for read/write of ByteBuffer

    @Override
    public String toString() {
        return this.convertBytesToString(this.recordBuffer.array());
    }

    // MARK: Overridden Methods

    /**
     * This will convert array of bytes to string.
     * It uses "UTF-8" charset.
     */
    private String convertBytesToString(byte[] bytes) {
        String converted = "";
        try {
            converted = new String(bytes, this.CHARSET_NAME);

        } catch (UnsupportedEncodingException e) {
            Utils.println("\nerror: cannot convert bytebuffer content to string due to encoding issue.\n");
        }
        return converted;
    }

    // MARK: Private Helper Methods

    /**
     * This will convert string to array of bytes.
     * It uses "UTF-8" charset.
     */
    private byte[] getBytes(String string) {
        try {
            return string.getBytes(this.CHARSET_NAME);

        } catch (UnsupportedEncodingException e) {
            Utils.println(e);
            return string.getBytes();
        }
    }

    private enum MODE {
        READ,
        WRITE
    }
}