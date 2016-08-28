package mhfc.net.common.network.message.quest;

import java.io.IOException;
import java.util.Objects;

import com.google.common.base.Throwables;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.data.QuestDescriptionRegistry;
import mhfc.net.common.core.directors.DirectorDownloadQuests;
import mhfc.net.common.core.directors.DirectorUploadQuests;

public class MessageQuestInit implements IMessage {

	protected QuestDescriptionRegistry data; // Used by upload
	protected DirectorDownloadQuests downloader; // Used by download

	public MessageQuestInit() {
		data = new QuestDescriptionRegistry();
	}

	public MessageQuestInit(QuestDescriptionRegistry dataObject) {
		this.data = Objects.requireNonNull(dataObject);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		String json = ByteBufUtils.readUTF8String(buf);
		downloader = new DirectorDownloadQuests(json);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		try {
			String json = DirectorUploadQuests.construct(data);
			ByteBufUtils.writeUTF8String(buf, json);
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	public void initialize(QuestDescriptionRegistry data) {
		downloader.construct(data);
	}
}
