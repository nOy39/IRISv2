/*
 * Copyright 2012-2014 Nikolay A. Viguro
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.iris.common.database.model.devices;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import ru.iris.common.database.model.DBModel;
import ru.iris.common.database.model.SensorData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "devices")
public class Device extends DBModel
{
	private String name = "not set";

	private short node = 0;

	private int zone = 0;

	private String type = "unknown";

	@Column(name = "internaltype")
	private String internalType = "unknown";

	@Column(name = "manufname")
	private String manufName = "unknown";

	@Column(name = "productname")
	private String productName = "unknown";

	private String uuid = "unknown";

	private String status = "unknown";

	@Column(name = "internalname")
	private String internalName = "unknown";

	private String source = "unknown";

	public Device()
	{
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getProductName()
	{
		return this.productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public short getNode()
	{
		return this.node;
	}

	public void setNode(short node)
	{
		this.node = node;
	}

	public String getUuid()
	{
		return this.uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public int getZone()
	{
		return this.zone;
	}

	public void setZone(int zone)
	{
		this.zone = zone;
	}

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getInternalName()
	{
		return internalName;
	}

	public void setInternalName(String internalName)
	{
		this.internalName = internalName;
	}

	public String getInternalType()
	{
		return this.internalType;
	}

	public void setInternalType(String type)
	{
		this.internalType = type;
	}

	public String getManufName()
	{
		return this.manufName;
	}

	public void setManufName(String manufName)
	{
		this.manufName = manufName;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<DeviceValue> getValues()
	{
		return Ebean.find(DeviceValue.class).where().eq("uuid", uuid).findList();
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public DeviceValue getValue(String label)
	{
		return Ebean.find(DeviceValue.class).where().and(Expr.eq("label", label), Expr.eq("uuid", this.getUuid())).findUnique();
	}

	public synchronized void removeValue(DeviceValue value)
	{
		Ebean.delete(value);
	}

	public synchronized void removeValue(String valuelabel)
	{
		DeviceValue deviceValue = Ebean.find(DeviceValue.class).where().and(Expr.eq("label", valuelabel), Expr.eq("uuid", this.getUuid())).findUnique();

		if (deviceValue != null)
			Ebean.delete(deviceValue);
	}

	public static Device getDeviceByUUID(String uuid)
	{
		return Ebean.find(Device.class).where().eq("uuid", uuid).findUnique();
	}

	public static Device getDeviceByNode(short node)
	{
		return Ebean.find(Device.class).where().eq("node", node).findUnique();
	}

	public List<SensorData> getLogs() {
		return Ebean.find(SensorData.class).where().eq("uuid", this.uuid).order().desc("logdate").findList();
	}

	@Override
	public String toString() {
		return "Device{" +
				"name='" + name + '\'' +
				", node=" + node +
				", zone=" + zone +
				", type='" + type + '\'' +
				", internalType='" + internalType + '\'' +
				", manufName='" + manufName + '\'' +
				", productName='" + productName + '\'' +
				", uuid='" + uuid + '\'' +
				", status='" + status + '\'' +
				", internalName='" + internalName + '\'' +
				", source='" + source + '\'' +
				'}';
	}
}
