import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;

public class showGraph {

	private static ArrayList<String> lines = new ArrayList<String>();


	public static void main(String[] args) throws IOException
	{
		try
		{

			String node = "";
			String fName = "";
			String nodeA = "";
			String nodeB = "";

			ArrayList<String> nodes = new ArrayList<String>();
			ArrayList<String> edges  = new ArrayList<String>();
			ArrayList<String> colorModel  = new ArrayList<String>();
			ArrayList<Double> colorValues  = new ArrayList<Double>();
			ArrayList<String> refillNodes  = new ArrayList<String>();
			ArrayList<Integer> refillColorIndice  = new ArrayList<Integer>();


			boolean isColorModelScale = false;


			Graph graph = new SingleGraph("Graph");
			graph.addAttribute("ui.stylesheet", "node.red {fill-color: red; }  node.green {fill-color: green; }  node.blue {fill-color: blue; }  edge {fill-color: grey; }");
			graph.display();



			if (args.length > 0)
			{
				fName = args[0];

				if (fName.contains(".txt"))
				{
					File dir = new File(".");
					File fin = new File(dir.getCanonicalPath() + File.separator + fName);
					readFile(fin);
				} else
					System.out.println("File's name is invalid.");
			} else {
				readStandardInput();
			}

			for (String line : lines)
			{
				line = line.trim();

				if (line.contains("rgb"))
					isColorModelScale = true;
				else
					isColorModelScale = false;

				if (isColorModelScale)
				{
					String[] temp;
					temp = line.split(" ");
					for (int i =0; i < temp.length ; i++)
					{
						if(temp[i].contains("rgb"))
						{
							colorModel.add(temp[i]);
						}
					}
				}

				/*
				 * A prototype code somehow does not work
				if (colorValues.isEmpty() && !isColorModelScale)
				{
					double value = 0;
					colorValues.add(value);

					for (int i = 2; i < colorModel.size() ; i++) {
						value = (float) (i - 1) / (colorModel.size() - 1);
						colorValues.add(value);
					}

					value = 1;
					colorValues.add(value);
				}*/

				if (colorValues.isEmpty() && !isColorModelScale)
				{
					double value = 0;
					for (int i = 1; i < colorModel.size() + 1; i++)
					{
						if (i == 1)
							value = 0;
						else if (i == colorModel.size())
							value = 1;
						else
						{
							value = (float) (i - 1) / (colorModel.size() - 1);
						}
						colorValues.add(value);
					}
				}

				if (line.length() > 4 && line.charAt(0) != '#')
				{
					if (line.substring(0, 4).equals("node"))
					{
						node = line.substring(5);
						if (!nodes.isEmpty())
						{
							if(!nodes.contains(node))
							{
								graph.addNode(node);
								nodes.add(node);
							} else {
								System.out.println("node " + "'" + node + "'" +" exists.");
							}
						} else {
							graph.addNode(node);
							nodes.add(node);
						}
					}

					if (line.substring(0, 4).equals("edge"))
					{
						int count = 0;
						line = line.substring(5);
						line = line.trim();

						nodeA = "";
						for (int i = 0; i < line.length(); i++)
						{
							if (Character.isWhitespace(line.charAt(i)))
								break;
							else
								nodeA += line.charAt(i);
							count++;
						}

						if (nodeA.equals(""))
							System.out.println("Error: Nodes have not been specified.");
						else if (!nodes.contains(nodeA))
							System.out.println("Error: Node " + "'" + nodeA + "'" + " does not exist.");
						else
						{
							nodeB = line.substring(count).trim();
							if(nodeB.equals(""))
								System.out.println("A node has not been specified.");
							else if (!nodes.contains(nodeB))
								System.out.println("Node " + "'" + nodeB + "'" + " does not exist.");
							else
							{
								if (edges.contains(nodeA+nodeB))
								{
									System.out.println("Edge " + "'" +nodeA+ " -- "+nodeB + "'" + " exists.");
								} else {
									graph.addEdge(nodeA+nodeB, nodeA, nodeB);
									edges.add(nodeA+nodeB);
									edges.add(nodeB+nodeA);
								}
							}
						}
					}
				}

				if (line.length() > 5 && line.charAt(0) != '#')
				{
					if (line.substring(0, 5).equals("color") && !line.contains("Model"))
					{
						int count = 0;
						String colorfulNode = "";
						String color = "";

						line = line.substring(6);
						line = line.trim();

						for (int i = 0; i < line.length(); i++)
						{
							if (Character.isWhitespace(line.charAt(i)))
								break;
							else
								colorfulNode += line.charAt(i);
							count++;
						}

						color = line.substring(count).trim();

						if (color.isEmpty())
							System.out.println("Error: Color has not been specified.");
						else {
							if (isColorValue(color))
							{
								float value = Float.parseFloat(color);
								value = value*10;

								float previousDifference = 0;
								float currentDifference = 0;
								int index = 0;

								for (int i = 0; i < colorValues.size(); i++)
								{
									if (i == 0)
									{
										previousDifference = value;
										currentDifference = value;
									} else if (i == colorValues.size() - 1) {
										index = colorValues.size() - 1;
									} else {
										currentDifference = (float) Math.abs(value - colorValues.get(i));
									}

									if (previousDifference < currentDifference)
									{
										index = i - 1;
										break;
									}

									previousDifference = currentDifference;
								}

								if (nodes.contains(colorfulNode))
									graph.getNode(colorfulNode).addAttribute("ui.style", "fill-color: " + colorModel.get(index) + ";");
								else {
									refillNodes.add(colorfulNode);
									refillColorIndice.add(index);
								}
							} else {
								if (!isColor(color))
									System.out.println("Error: Color " + "'" + color + "'" + " does not exist.");
								else {
									if (nodes.contains(colorfulNode))
										graph.getNode(colorfulNode).addAttribute("ui.class", color);
									else
										System.out.println("Error: Node " + "'" + colorfulNode + "'" + " does not exist.");
								}
							}
						}
					}
				}
			}

			if (!refillNodes.isEmpty())
			{
				for (int i = 0; i < refillNodes.size(); i++)
				{
					String colorfulNode = refillNodes.get(i);
					int index = refillColorIndice.get(i);

					if (nodes.contains(colorfulNode))
						graph.getNode(colorfulNode).addAttribute("ui.style", "fill-color: " + colorModel.get(index) + ";");
					else {
						System.out.println("Error: Node " + "'" + colorfulNode + "'" + " does not exist.");
					}
				}
			}
		}

		catch (IOException e){
			System.out.println("Error: File does not exit");
		}
	}

	public static Boolean isColor(String color)
	{
		if (color.equals("red") || color.equals("blue") || color.equals("green"))
			return true;
		else
			return false;
	}

	public static Boolean isColorValue(String colorValue)
	{
		if (Character.isDigit(colorValue.charAt(0)))
			return true;
		else
			return false;
	}

	private static void readFile(File fin) throws IOException
	{
		try
		{
			FileInputStream fis = new FileInputStream(fin);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}

			br.close();
		}
		catch (IOException e)
		{
			System.out.println("File does not exit");
		}
	}

	private static void readStandardInput() throws IOException
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in), 1);

		String line = null;
		while ((line = stdin.readLine()) != null)
		{
			lines.add(line);
		}
	}
}