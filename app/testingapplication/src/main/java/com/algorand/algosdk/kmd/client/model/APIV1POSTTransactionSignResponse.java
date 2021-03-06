/*
 * for KMD HTTP API
 * API for KMD (Key Management Daemon)
 *
 * OpenAPI spec version: 0.0.1
 * Contact: contact@algorand.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.algorand.algosdk.kmd.client.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ObjectUtils;

/**
 * APIV1POSTTransactionSignResponse is the repsonse to &#x60;POST /v1/transaction/sign&#x60; friendly:SignTransactionResponse
 */
@ApiModel(description = "APIV1POSTTransactionSignResponse is the repsonse to `POST /v1/transaction/sign` friendly:SignTransactionResponse")

public class APIV1POSTTransactionSignResponse {
  @SerializedName("error")
  private Boolean error = null;

  @SerializedName("message")
  private String message = null;

  @SerializedName("signed_transaction")
  private byte[] signedTransaction = null;

  public APIV1POSTTransactionSignResponse error(Boolean error) {
    this.error = error;
    return this;
  }

   /**
   * Get error
   * @return error
  **/
  @ApiModelProperty(value = "")
  public Boolean isError() {
    return error;
  }

  public void setError(Boolean error) {
    this.error = error;
  }

  public APIV1POSTTransactionSignResponse message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public APIV1POSTTransactionSignResponse signedTransaction(byte[] signedTransaction) {
    this.signedTransaction = signedTransaction;
    return this;
  }

   /**
   * Get signedTransaction
   * @return signedTransaction
  **/
  @ApiModelProperty(value = "")
  public byte[] getSignedTransaction() {
    return signedTransaction;
  }

  public void setSignedTransaction(byte[] signedTransaction) {
    this.signedTransaction = signedTransaction;
  }


  @Override
  public boolean equals(Object o) {
  if (this == o) {
    return true;
  }
  if (o == null || getClass() != o.getClass()) {
    return false;
  }
    APIV1POSTTransactionSignResponse apIV1POSTTransactionSignResponse = (APIV1POSTTransactionSignResponse) o;
    return ObjectUtils.equals(this.error, apIV1POSTTransactionSignResponse.error) &&
    ObjectUtils.equals(this.message, apIV1POSTTransactionSignResponse.message) &&
    ObjectUtils.equals(this.signedTransaction, apIV1POSTTransactionSignResponse.signedTransaction);
  }

  @Override
  public int hashCode() {
    return ObjectUtils.hashCodeMulti(error, message, signedTransaction);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class APIV1POSTTransactionSignResponse {\n");
    
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    signedTransaction: ").append(toIndentedString(signedTransaction)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

