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
 * Structure to encapsulate log setting
 */
public class emLogServerSettings implements org.apache.thrift.TBase<emLogServerSettings, emLogServerSettings._Fields>, java.io.Serializable, Cloneable, Comparable<emLogServerSettings> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("emLogServerSettings");

  private static final org.apache.thrift.protocol.TField LOG_SERVER_FIELD_DESC = new org.apache.thrift.protocol.TField("logServer", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField EXPERIMENT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("experimentName", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new emLogServerSettingsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new emLogServerSettingsTupleSchemeFactory());
  }

  /**
   * URL for log server
   */
  public String logServer; // required
  /**
   * Name of experiment
   */
  public String experimentName; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * URL for log server
     */
    LOG_SERVER((short)1, "logServer"),
    /**
     * Name of experiment
     */
    EXPERIMENT_NAME((short)2, "experimentName");

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
        case 1: // LOG_SERVER
          return LOG_SERVER;
        case 2: // EXPERIMENT_NAME
          return EXPERIMENT_NAME;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LOG_SERVER, new org.apache.thrift.meta_data.FieldMetaData("logServer", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EXPERIMENT_NAME, new org.apache.thrift.meta_data.FieldMetaData("experimentName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(emLogServerSettings.class, metaDataMap);
  }

  public emLogServerSettings() {
  }

  public emLogServerSettings(
    String logServer,
    String experimentName)
  {
    this();
    this.logServer = logServer;
    this.experimentName = experimentName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public emLogServerSettings(emLogServerSettings other) {
    if (other.isSetLogServer()) {
      this.logServer = other.logServer;
    }
    if (other.isSetExperimentName()) {
      this.experimentName = other.experimentName;
    }
  }

  public emLogServerSettings deepCopy() {
    return new emLogServerSettings(this);
  }

  @Override
  public void clear() {
    this.logServer = null;
    this.experimentName = null;
  }

  /**
   * URL for log server
   */
  public String getLogServer() {
    return this.logServer;
  }

  /**
   * URL for log server
   */
  public emLogServerSettings setLogServer(String logServer) {
    this.logServer = logServer;
    return this;
  }

  public void unsetLogServer() {
    this.logServer = null;
  }

  /** Returns true if field logServer is set (has been assigned a value) and false otherwise */
  public boolean isSetLogServer() {
    return this.logServer != null;
  }

  public void setLogServerIsSet(boolean value) {
    if (!value) {
      this.logServer = null;
    }
  }

  /**
   * Name of experiment
   */
  public String getExperimentName() {
    return this.experimentName;
  }

  /**
   * Name of experiment
   */
  public emLogServerSettings setExperimentName(String experimentName) {
    this.experimentName = experimentName;
    return this;
  }

  public void unsetExperimentName() {
    this.experimentName = null;
  }

  /** Returns true if field experimentName is set (has been assigned a value) and false otherwise */
  public boolean isSetExperimentName() {
    return this.experimentName != null;
  }

  public void setExperimentNameIsSet(boolean value) {
    if (!value) {
      this.experimentName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LOG_SERVER:
      if (value == null) {
        unsetLogServer();
      } else {
        setLogServer((String)value);
      }
      break;

    case EXPERIMENT_NAME:
      if (value == null) {
        unsetExperimentName();
      } else {
        setExperimentName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LOG_SERVER:
      return getLogServer();

    case EXPERIMENT_NAME:
      return getExperimentName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LOG_SERVER:
      return isSetLogServer();
    case EXPERIMENT_NAME:
      return isSetExperimentName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof emLogServerSettings)
      return this.equals((emLogServerSettings)that);
    return false;
  }

  public boolean equals(emLogServerSettings that) {
    if (that == null)
      return false;

    boolean this_present_logServer = true && this.isSetLogServer();
    boolean that_present_logServer = true && that.isSetLogServer();
    if (this_present_logServer || that_present_logServer) {
      if (!(this_present_logServer && that_present_logServer))
        return false;
      if (!this.logServer.equals(that.logServer))
        return false;
    }

    boolean this_present_experimentName = true && this.isSetExperimentName();
    boolean that_present_experimentName = true && that.isSetExperimentName();
    if (this_present_experimentName || that_present_experimentName) {
      if (!(this_present_experimentName && that_present_experimentName))
        return false;
      if (!this.experimentName.equals(that.experimentName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(emLogServerSettings other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetLogServer()).compareTo(other.isSetLogServer());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLogServer()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.logServer, other.logServer);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetExperimentName()).compareTo(other.isSetExperimentName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExperimentName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.experimentName, other.experimentName);
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
    StringBuilder sb = new StringBuilder("emLogServerSettings(");
    boolean first = true;

    sb.append("logServer:");
    if (this.logServer == null) {
      sb.append("null");
    } else {
      sb.append(this.logServer);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("experimentName:");
    if (this.experimentName == null) {
      sb.append("null");
    } else {
      sb.append(this.experimentName);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class emLogServerSettingsStandardSchemeFactory implements SchemeFactory {
    public emLogServerSettingsStandardScheme getScheme() {
      return new emLogServerSettingsStandardScheme();
    }
  }

  private static class emLogServerSettingsStandardScheme extends StandardScheme<emLogServerSettings> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, emLogServerSettings struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LOG_SERVER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.logServer = iprot.readString();
              struct.setLogServerIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EXPERIMENT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.experimentName = iprot.readString();
              struct.setExperimentNameIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, emLogServerSettings struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.logServer != null) {
        oprot.writeFieldBegin(LOG_SERVER_FIELD_DESC);
        oprot.writeString(struct.logServer);
        oprot.writeFieldEnd();
      }
      if (struct.experimentName != null) {
        oprot.writeFieldBegin(EXPERIMENT_NAME_FIELD_DESC);
        oprot.writeString(struct.experimentName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class emLogServerSettingsTupleSchemeFactory implements SchemeFactory {
    public emLogServerSettingsTupleScheme getScheme() {
      return new emLogServerSettingsTupleScheme();
    }
  }

  private static class emLogServerSettingsTupleScheme extends TupleScheme<emLogServerSettings> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, emLogServerSettings struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLogServer()) {
        optionals.set(0);
      }
      if (struct.isSetExperimentName()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetLogServer()) {
        oprot.writeString(struct.logServer);
      }
      if (struct.isSetExperimentName()) {
        oprot.writeString(struct.experimentName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, emLogServerSettings struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.logServer = iprot.readString();
        struct.setLogServerIsSet(true);
      }
      if (incoming.get(1)) {
        struct.experimentName = iprot.readString();
        struct.setExperimentNameIsSet(true);
      }
    }
  }

}

