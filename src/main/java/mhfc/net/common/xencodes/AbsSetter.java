package mhfc.net.common.xencodes;

import io.netty.buffer.ByteBuf;

import java.util.List;

import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.Statement;

public class AbsSetter extends Block{

	public long tokengetter;
	public int stamplinefinish;
	public int rettick = 100;
	
	public AbsSetter(long token, int finish, List<Statement> statements) {
		super(token, finish, statements);
		tokengetter = token;
		stamplinefinish = finish % rettick;
	}
	
	public void registerAbsSetter(long f, int f1){
		tokengetter = f;
		stamplinefinish = f1;
	}
	
	public void transmit(long par1, ByteBuf buf){
		buf.writeInt(stamplinefinish);
		buf.writeLong(tokengetter);
	}
	
	public void getTransmit(ByteBuf buf){
		stamplinefinish = buf.readInt();
		tokengetter = buf.readLong();
	}

}
