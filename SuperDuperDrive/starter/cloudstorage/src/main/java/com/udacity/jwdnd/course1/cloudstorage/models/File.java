package com.udacity.rw.course1.cloudstorage.models;

public class File {
  private int fileId;
  private int userId;
  private String filename;
  private String contentType;
  private String fileSize;
  private byte[] fileData;

  public File() {}

  public File(int fileId, int userId, String filename,
              String contentType, String fileSize, byte[] fileData) {
    this.fileId = fileId;
    this.userId = userId;
    this.filename = filename;
    this.contentType = contentType;
    this.fileData = fileData;
    this.fileSize = fileSize;
  }

  public int getFileId() {
    return fileId;
  }

  public void setFileId(int fileId) {
    this.fileId = fileId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

  public String getFileSize() {
    return fileSize;
  }

  public void setFileSize(String fileSize) {
    this.fileSize = fileSize;
  }
}
