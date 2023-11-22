package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the EggData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "EggData", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class EggData implements Model {
  public static final QueryField ID = field("EggData", "id");
  public static final QueryField TELEMETRY = field("EggData", "telemetry");
  public static final QueryField TELEMETRY_TIMESTAMP = field("EggData", "telemetryTimestamp");
  public static final QueryField EGG_TYPE = field("EggData", "eggType");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String telemetry;
  private final @ModelField(targetType="String") String telemetryTimestamp;
  private final @ModelField(targetType="String") String eggType;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getTelemetry() {
      return telemetry;
  }
  
  public String getTelemetryTimestamp() {
      return telemetryTimestamp;
  }
  
  public String getEggType() {
      return eggType;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private EggData(String id, String telemetry, String telemetryTimestamp, String eggType) {
    this.id = id;
    this.telemetry = telemetry;
    this.telemetryTimestamp = telemetryTimestamp;
    this.eggType = eggType;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      EggData eggData = (EggData) obj;
      return ObjectsCompat.equals(getId(), eggData.getId()) &&
              ObjectsCompat.equals(getTelemetry(), eggData.getTelemetry()) &&
              ObjectsCompat.equals(getTelemetryTimestamp(), eggData.getTelemetryTimestamp()) &&
              ObjectsCompat.equals(getEggType(), eggData.getEggType()) &&
              ObjectsCompat.equals(getCreatedAt(), eggData.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), eggData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTelemetry())
      .append(getTelemetryTimestamp())
      .append(getEggType())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("EggData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("telemetry=" + String.valueOf(getTelemetry()) + ", ")
      .append("telemetryTimestamp=" + String.valueOf(getTelemetryTimestamp()) + ", ")
      .append("eggType=" + String.valueOf(getEggType()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static EggData justId(String id) {
    return new EggData(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      telemetry,
      telemetryTimestamp,
      eggType);
  }
  public interface BuildStep {
    EggData build();
    BuildStep id(String id);
    BuildStep telemetry(String telemetry);
    BuildStep telemetryTimestamp(String telemetryTimestamp);
    BuildStep eggType(String eggType);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String telemetry;
    private String telemetryTimestamp;
    private String eggType;
    public Builder() {
      
    }
    
    private Builder(String id, String telemetry, String telemetryTimestamp, String eggType) {
      this.id = id;
      this.telemetry = telemetry;
      this.telemetryTimestamp = telemetryTimestamp;
      this.eggType = eggType;
    }
    
    @Override
     public EggData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new EggData(
          id,
          telemetry,
          telemetryTimestamp,
          eggType);
    }
    
    @Override
     public BuildStep telemetry(String telemetry) {
        this.telemetry = telemetry;
        return this;
    }
    
    @Override
     public BuildStep telemetryTimestamp(String telemetryTimestamp) {
        this.telemetryTimestamp = telemetryTimestamp;
        return this;
    }
    
    @Override
     public BuildStep eggType(String eggType) {
        this.eggType = eggType;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String telemetry, String telemetryTimestamp, String eggType) {
      super(id, telemetry, telemetryTimestamp, eggType);
      
    }
    
    @Override
     public CopyOfBuilder telemetry(String telemetry) {
      return (CopyOfBuilder) super.telemetry(telemetry);
    }
    
    @Override
     public CopyOfBuilder telemetryTimestamp(String telemetryTimestamp) {
      return (CopyOfBuilder) super.telemetryTimestamp(telemetryTimestamp);
    }
    
    @Override
     public CopyOfBuilder eggType(String eggType) {
      return (CopyOfBuilder) super.eggType(eggType);
    }
  }
  

  public static class EggDataIdentifier extends ModelIdentifier<EggData> {
    private static final long serialVersionUID = 1L;
    public EggDataIdentifier(String id) {
      super(id);
    }
  }
  
}
