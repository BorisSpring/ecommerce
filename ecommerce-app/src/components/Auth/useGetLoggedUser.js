import { useQuery } from "@tanstack/react-query";
import { getLoggedUser } from "../../api/actions";

export function useGetLoggedUser() {
  const { data: loggedUser } = useQuery({
    queryFn: async () => {
      const data = await getLoggedUser();
      return data;
    },
    queryKey: ["loggedUser"],
  });
  return { loggedUser };
}
