package mhfc.net.common.network.packet;

import java.io.IOException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import mhfc.net.common.core.data.QuestDescriptionRegistryData;
import mhfc.net.common.core.directors.DirectorDownloadQuests;
import mhfc.net.common.core.directors.DirectorUploadQuests;

public class MessageQuestInit implements IMessage {

	protected QuestDescriptionRegistryData data;

	public MessageQuestInit() {
		data = new QuestDescriptionRegistryData();
	}

	public MessageQuestInit(QuestDescriptionRegistryData dataObject) {
		this.data = dataObject;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		buf.retain();
		try (ByteBufInputStream in = new ByteBufInputStream(buf);) {
			DirectorDownloadQuests downloader = new DirectorDownloadQuests(in);
			downloader.construct(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.release();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try (ByteBufOutputStream out = new ByteBufOutputStream(buf);) {
			DirectorUploadQuests uploader = new DirectorUploadQuests(out);
			uploader.construct(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public QuestDescriptionRegistryData getQuestDescriptionData() {
		return data;
	}
}
