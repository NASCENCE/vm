/**
 * Autogenerated by Thrift Compiler (1.0.0-dev)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package emInterfaces;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The device directly controlling the EM should make sure sensible things are happening, and if not throw an exception.  All methods should return problems via exceptions.  Exceptions should contain as much information as possible.  Exceptions can be fatal or non fatal. On a fatal exception, the controlling program should probably abandon what it is doing and reset.
 */
public class emException extends TException implements org.apache.thrift.TBase<emException, emException._Fields>, java.io.Serializable, Cloneable, Comparable<emException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("emException");

  private static final org.apache.thrift.protocol.TField ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorCode", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField REASON_FIELD_DESC = new org.apache.thrift.protocol.TField("Reason", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SOURCE_FIELD_DESC = new org.apache.thrift.protocol.TField("Source", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField EXCEPTION_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("exceptionType", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new emExceptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new emExceptionTupleSchemeFactory());
  }

  /**
   * Error codes, to be defined in documentation
   */
  public int errorCode; // required
  /**
   * Why the error was thrown
   */
  public String Reason; // required
  /**
   * Where (e.g. module, line number) the error came from
   */
  public String Source; // required
  /**
   * What type of error this is
   * 
   * @see emExceptionType
   */
  public emExceptionType exceptionType; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * Error codes, to be defined in documentation
     */
    ERROR_CODE((short)1, "errorCode"),
    /**
     * Why the error was thrown
     */
    REASON((short)2, "Reason"),
    /**
     * Where (e.g. module, line number) the error came from
     */
    SOURCE((short)3, "Source"),
    /**
     * What type of error this is
     * 
     * @see emExceptionType
     */
    EXCEPTION_TYPE((short)4, "exceptionType");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ERROR_CODE
          return ERROR_CODE;
        case 2: // REASON
          return REASON;
        case 3: // SOURCE
          return SOURCE;
        case 4: // EXCEPTION_TYPE
          return EXCEPTION_TYPE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ERRORCODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("errorCode", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REASON, new org.apache.thrift.meta_data.FieldMetaData("Reason", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SOURCE, new org.apache.thrift.meta_data.FieldMetaData("Source", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EXCEPTION_TYPE, new org.apache.thrift.meta_data.FieldMetaData("exceptionType", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, emExceptionType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(emException.class, metaDataMap);
  }

  public emException() {
  }

  public emException(
    int errorCode,
    String Reason,
    String Source,
    emExceptionType exceptionType)
  {
    this();
    this.errorCode = errorCode;
    setErrorCodeIsSet(true);
    this.Reason = Reason;
    this.Source = Source;
    this.exceptionType = exceptionType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public emException(emException other) {
    __isset_bitfield = other.__isset_bitfield;
    this.errorCode = other.errorCode;
    if (other.isSetReason()) {
      this.Reason = other.Reason;
    }
    if (other.isSetSource()) {
      this.Source = other.Source;
    }
    if (other.isSetExceptionType()) {
      this.exceptionType = other.exceptionType;
    }
  }

  public emException deepCopy() {
    return new emException(this);
  }

  @Override
  public void clear() {
    setErrorCodeIsSet(false);
    this.errorCode = 0;
    this.Reason = null;
    this.Source = null;
    this.exceptionType = null;
  }

  /**
   * Error codes, to be defined in documentation
   */
  public int getErrorCode() {
    return this.errorCode;
  }

  /**
   * Error codes, to be defined in documentation
   */
  public emException setErrorCode(int errorCode) {
    this.errorCode = errorCode;
    setErrorCodeIsSet(true);
    return this;
  }

  public void unsetErrorCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ERRORCODE_ISSET_ID);
  }

  /** Returns true if field errorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorCode() {
    return EncodingUtils.testBit(__isset_bitfield, __ERRORCODE_ISSET_ID);
  }

  public void setErrorCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ERRORCODE_ISSET_ID, value);
  }

  /**
   * Why the error was thrown
   */
  public String getReason() {
    return this.Reason;
  }

  /**
   * Why the error was thrown
   */
  public emException setReason(String Reason) {
    this.Reason = Reason;
    return this;
  }

  public void unsetReason() {
    this.Reason = null;
  }

  /** Returns true if field Reason is set (has been assigned a value) and false otherwise */
  public boolean isSetReason() {
    return this.Reason != null;
  }

  public void setReasonIsSet(boolean value) {
    if (!value) {
      this.Reason = null;
    }
  }

  /**
   * Where (e.g. module, line number) the error came from
   */
  public String getSource() {
    return this.Source;
  }

  /**
   * Where (e.g. module, line number) the error came from
   */
  public emException setSource(String Source) {
    this.Source = Source;
    return this;
  }

  public void unsetSource() {
    this.Source = null;
  }

  /** Returns true if field Source is set (has been assigned a value) and false otherwise */
  public boolean isSetSource() {
    return this.Source != null;
  }

  public void setSourceIsSet(boolean value) {
    if (!value) {
      this.Source = null;
    }
  }

  /**
   * What type of error this is
   * 
   * @see emExceptionType
   */
  public emExceptionType getExceptionType() {
    return this.exceptionType;
  }

  /**
   * What type of error this is
   * 
   * @see emExceptionType
   */
  public emException setExceptionType(emExceptionType exceptionType) {
    this.exceptionType = exceptionType;
    return this;
  }

  public void unsetExceptionType() {
    this.exceptionType = null;
  }

  /** Returns true if field exceptionType is set (has been assigned a value) and false otherwise */
  public boolean isSetExceptionType() {
    return this.exceptionType != null;
  }

  public void setExceptionTypeIsSet(boolean value) {
    if (!value) {
      this.exceptionType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ERROR_CODE:
      if (value == null) {
        unsetErrorCode();
      } else {
        setErrorCode((Integer)value);
      }
      break;

    case REASON:
      if (value == null) {
        unsetReason();
      } else {
        setReason((String)value);
      }
      break;

    case SOURCE:
      if (value == null) {
        unsetSource();
      } else {
        setSource((String)value);
      }
      break;

    case EXCEPTION_TYPE:
      if (value == null) {
        unsetExceptionType();
      } else {
        setExceptionType((emExceptionType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR_CODE:
      return Integer.valueOf(getErrorCode());

    case REASON:
      return getReason();

    case SOURCE:
      return getSource();

    case EXCEPTION_TYPE:
      return getExceptionType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ERROR_CODE:
      return isSetErrorCode();
    case REASON:
      return isSetReason();
    case SOURCE:
      return isSetSource();
    case EXCEPTION_TYPE:
      return isSetExceptionType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof emException)
      return this.equals((emException)that);
    return false;
  }

  public boolean equals(emException that) {
    if (that == null)
      return false;

    boolean this_present_errorCode = true;
    boolean that_present_errorCode = true;
    if (this_present_errorCode || that_present_errorCode) {
      if (!(this_present_errorCode && that_present_errorCode))
        return false;
      if (this.errorCode != that.errorCode)
        return false;
    }

    boolean this_present_Reason = true && this.isSetReason();
    boolean that_present_Reason = true && that.isSetReason();
    if (this_present_Reason || that_present_Reason) {
      if (!(this_present_Reason && that_present_Reason))
        return false;
      if (!this.Reason.equals(that.Reason))
        return false;
    }

    boolean this_present_Source = true && this.isSetSource();
    boolean that_present_Source = true && that.isSetSource();
    if (this_present_Source || that_present_Source) {
      if (!(this_present_Source && that_present_Source))
        return false;
      if (!this.Source.equals(that.Source))
        return false;
    }

    boolean this_present_exceptionType = true && this.isSetExceptionType();
    boolean that_present_exceptionType = true && that.isSetExceptionType();
    if (this_present_exceptionType || that_present_exceptionType) {
      if (!(this_present_exceptionType && that_present_exceptionType))
        return false;
      if (!this.exceptionType.equals(that.exceptionType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(emException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetErrorCode()).compareTo(other.isSetErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorCode, other.errorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReason()).compareTo(other.isSetReason());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReason()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.Reason, other.Reason);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSource()).compareTo(other.isSetSource());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSource()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.Source, other.Source);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExceptionType()).compareTo(other.isSetExceptionType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExceptionType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.exceptionType, other.exceptionType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("emException(");
    boolean first = true;

    sb.append("errorCode:");
    sb.append(this.errorCode);
    first = false;
    if (!first) sb.append(", ");
    sb.append("Reason:");
    if (this.Reason == null) {
      sb.append("null");
    } else {
      sb.append(this.Reason);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("Source:");
    if (this.Source == null) {
      sb.append("null");
    } else {
      sb.append(this.Source);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("exceptionType:");
    if (this.exceptionType == null) {
      sb.append("null");
    } else {
      sb.append(this.exceptionType);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class emExceptionStandardSchemeFactory implements SchemeFactory {
    public emExceptionStandardScheme getScheme() {
      return new emExceptionStandardScheme();
    }
  }

  private static class emExceptionStandardScheme extends StandardScheme<emException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, emException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.errorCode = iprot.readI32();
              struct.setErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REASON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.Reason = iprot.readString();
              struct.setReasonIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SOURCE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.Source = iprot.readString();
              struct.setSourceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // EXCEPTION_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.exceptionType = emExceptionType.findByValue(iprot.readI32());
              struct.setExceptionTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, emException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ERROR_CODE_FIELD_DESC);
      oprot.writeI32(struct.errorCode);
      oprot.writeFieldEnd();
      if (struct.Reason != null) {
        oprot.writeFieldBegin(REASON_FIELD_DESC);
        oprot.writeString(struct.Reason);
        oprot.writeFieldEnd();
      }
      if (struct.Source != null) {
        oprot.writeFieldBegin(SOURCE_FIELD_DESC);
        oprot.writeString(struct.Source);
        oprot.writeFieldEnd();
      }
      if (struct.exceptionType != null) {
        oprot.writeFieldBegin(EXCEPTION_TYPE_FIELD_DESC);
        oprot.writeI32(struct.exceptionType.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class emExceptionTupleSchemeFactory implements SchemeFactory {
    public emExceptionTupleScheme getScheme() {
      return new emExceptionTupleScheme();
    }
  }

  private static class emExceptionTupleScheme extends TupleScheme<emException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, emException struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetErrorCode()) {
        optionals.set(0);
      }
      if (struct.isSetReason()) {
        optionals.set(1);
      }
      if (struct.isSetSource()) {
        optionals.set(2);
      }
      if (struct.isSetExceptionType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetErrorCode()) {
        oprot.writeI32(struct.errorCode);
      }
      if (struct.isSetReason()) {
        oprot.writeString(struct.Reason);
      }
      if (struct.isSetSource()) {
        oprot.writeString(struct.Source);
      }
      if (struct.isSetExceptionType()) {
        oprot.writeI32(struct.exceptionType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, emException struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.errorCode = iprot.readI32();
        struct.setErrorCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.Reason = iprot.readString();
        struct.setReasonIsSet(true);
      }
      if (incoming.get(2)) {
        struct.Source = iprot.readString();
        struct.setSourceIsSet(true);
      }
      if (incoming.get(3)) {
        struct.exceptionType = emExceptionType.findByValue(iprot.readI32());
        struct.setExceptionTypeIsSet(true);
      }
    }
  }

}

